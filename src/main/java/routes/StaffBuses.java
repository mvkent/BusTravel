package routes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import beans.Bus;
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
public class StaffBuses extends BaseRoute {
    private static final Logger logger = LoggerFactory.getLogger(StaffBuses.class);

    public StaffBuses(String path, Template template) throws IOException {
        super(path, template);
    }

    @Override
    public SimpleHash setResponse(Request request, Response response, SimpleHash root)
            throws IOException, TemplateException, SQLException, NoEnoughDataException {

        if(request.queryParams("do") != null){
            String action = request.queryParams("do");
            switch (action){
            case "add":
                createBus(request.queryParams("number"), request.queryParams("sits"));
                break;
            case "del":
                removeBus(request.queryParams("id"));
                response.redirect("/staff/buses");
                break;
            }
        }

        root.put("page", "buses");
        root.put("list", getList());
        return root;
    }

    private List<Bus> getList() throws SQLException {
        List<Bus> res = new ArrayList<>();
        ResultSet resultSet = DbConnector.fetchRows("SELECT id, num, sits FROM bus");
        while (resultSet.next()) {
            Bus bus = new Bus(resultSet.getLong(1), resultSet.getString(2), resultSet.getInt(3));
            res.add(bus);
        }
        return res;
    }

    private void createBus(String number, String sits) throws NoEnoughDataException, SQLException {
        try {
            if (number.isEmpty()) {
                throw new NullPointerException();
            }

            DbConnector.updateRows("INSERT INTO bus(num, sits) VALUES(?,?)", number, Integer.valueOf(sits));
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No bus data specified");
        }
    }

    private void removeBus(String idStr) throws NoEnoughDataException, SQLException {
        try {
            long id = Long.valueOf(idStr);
            DbConnector.updateRows("DELETE FROM bus WHERE id=?", id);
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No data specified");
        }
    }
}
