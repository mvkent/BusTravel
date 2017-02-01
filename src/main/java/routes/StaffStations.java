package routes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import beans.Station;
import exceptions.NoEnoughDataException;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import utils.DbConnector;

/**
 * Created by mvkent on 1/18/2017.
 */
public class StaffStations extends BaseRoute {
    private static final Logger logger = LoggerFactory.getLogger(StaffStations.class);

    public StaffStations(String path, Template template) throws IOException {
        super(path, template);
    }

    @Override
    public SimpleHash setResponse(Request request, Response response, SimpleHash root)
            throws IOException, TemplateException, SQLException, NoEnoughDataException {

        if(request.queryParams("do") != null){
            String action = request.queryParams("do");
            switch (action){
            case "add":
                createStation(request.queryParams("title"));
                break;
            case "del":
                removeStation(request.queryParams("id"));
                response.redirect("/staff/stations");
                break;
            }
        }

        root.put("page", "stations");
        root.put("list", getList());
        return root;
    }

    private List<Station> getList() throws SQLException {
        List<Station> res = new ArrayList<>();
        ResultSet resultSet = DbConnector.fetchRows("SELECT id, title FROM station");
        while (resultSet.next()) {
            Station station = new Station(resultSet.getLong(1), resultSet.getString(2));
            res.add(station);
        }
        return res;
    }

    private void createStation(String title) throws NoEnoughDataException, SQLException {
        try {
            if (title.isEmpty()) {
                throw new NullPointerException();
            }
            DbConnector.updateRows("INSERT INTO STATION(TITLE) VALUES(?)", title);
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No title specified");
        }
    }

    private void removeStation(String idStr) throws NoEnoughDataException, SQLException {
        try {
            long id = Long.valueOf(idStr);
            DbConnector.updateRows("DELETE FROM station WHERE id=?", id);
            DbConnector.updateRows("DELETE FROM route WHERE station_id=?", id);
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No title specified");
        }
    }
}
