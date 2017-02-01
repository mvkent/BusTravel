package routes;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import beans.Bus;
import beans.BusRoute;
import beans.Station;
import exceptions.NoEnoughDataException;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import services.StaffService;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.DbConnector;

import static spark.Spark.get;
import static spark.Spark.post;

public abstract class BaseRoute extends Route {
    public static String STAFF_ATTR = "staff";
    public static String ERROR_MESSAGE = "ERROR";
    private static final Logger logger = LoggerFactory.getLogger(BaseRoute.class);
    List<String> errors = new ArrayList<>();
    Template template;

    public BaseRoute(String path, Template template) throws IOException {
        super(path);
        this.template = template;
        get(this);
        post(this);
    }

    public abstract SimpleHash setResponse(Request request, Response response, SimpleHash root)
            throws IOException, TemplateException, SQLException, NoEnoughDataException;

    public void onHandler(Request request, Response response, Writer writer)
            throws IOException, TemplateException, SQLException {
        SimpleHash root = new SimpleHash();
        root.put(STAFF_ATTR, StaffService.getInstance().current(request));
        try {
            root = setResponse(request, response, root);
        } catch (NoEnoughDataException e) {
            logger.error(request.queryString(), e);
            root.put(ERROR_MESSAGE, e.getMessage());
        }
        generateTemplate(root, writer);
    }

    public void generateTemplate(SimpleHash simpleHash, Writer writer) throws IOException, TemplateException {
        this.template.process(simpleHash, writer);
        errors = new ArrayList<>();
    }

    public String getSessionId(Request request, Response response) {
        String sessionId = request.cookie("JSESSIONID");
        if (sessionId == null) {
            sessionId = initSessionId(request, response);
        }
        return sessionId;
    }

    private String initSessionId(Request request, Response response) {
        String sessionId = request.session(true).id();
        response.cookie("JSESSIONID", sessionId);
        return sessionId;
    }

    @Override
    public Object handle(Request request, Response response) {
        StringWriter writer = new StringWriter();
        try {
            onHandler(request, response, writer);
        } catch (SQLException e) {
            e.printStackTrace();
            response.redirect("/");
        } catch (IOException e) {
            e.printStackTrace();
            response.redirect("/");
        } catch (TemplateException e) {
            e.printStackTrace();
            response.redirect("/");
        }
        return writer;
    }

    public List<Station> getStations() throws SQLException {
        List<Station> res = new LinkedList<>();
        ResultSet resultSet = DbConnector.fetchRows("SELECT id, title FROM station");
        while (resultSet.next()) {
            Station bus = new Station(resultSet.getLong(1), resultSet.getString(2));
            res.add(bus);
        }
        return res;
    }

    public List<BusRoute> getRoutes() throws SQLException {
        List<BusRoute> res = new LinkedList<>();
        ResultSet resultSet = DbConnector
                .fetchRows("SELECT id, bus_id, station_id, sort, duration FROM route ORDER BY bus_id, sort, id");
        while (resultSet.next()) {
            BusRoute bus = new BusRoute(resultSet.getLong(1), resultSet.getLong(2), resultSet.getLong(3),
                    resultSet.getInt(4), resultSet.getInt(5));
            res.add(bus);
        }
        return res;
    }

    public List<Bus> getBuses() throws SQLException {
        List<Bus> res = new LinkedList<>();
        ResultSet resultSet = DbConnector.fetchRows("SELECT id, num, sits FROM bus");
        while (resultSet.next()) {
            Bus bus = new Bus(resultSet.getLong(1), resultSet.getString(2), resultSet.getInt(3));
            res.add(bus);
        }
        return res;
    }
}
