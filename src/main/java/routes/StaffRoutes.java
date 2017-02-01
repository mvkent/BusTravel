package routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import beans.BusRoute;
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
public class StaffRoutes extends BaseRoute {
    private static final Logger logger = LoggerFactory.getLogger(StaffRoutes.class);

    public StaffRoutes(String path, Template template) throws IOException {
        super(path, template);
    }

    @Override
    public SimpleHash setResponse(Request request, Response response, SimpleHash root)
            throws IOException, TemplateException, SQLException, NoEnoughDataException {

        if (request.queryParams("do") != null) {
            String action = request.queryParams("do");
            switch (action) {
            case "add":
                createRoute(request.queryParams("bus"), request.queryParams("station"),
                        request.queryParams("duration"));
                response.redirect("/staff/routes");
                break;
            case "move":
                String operator = request.queryParams("operator");
                if (operator.equals("x")) {
                    removeRoute(request.queryParams("id"));
                } else if (operator.equals("v")) {
                    moveDown(request.queryParams("id"), request.queryParams("bus"));
                } else if (operator.equals("^")) {
                    moveUp(request.queryParams("id"), request.queryParams("bus"));
                }
                response.redirect("/staff/routes");
                break;
            case "del":
                removeRoute(request.queryParams("id"));
                response.redirect("/staff/routes");
                break;
            }
        }

        root.put("page", "routes");
        root.put("stations", getStations());
        root.put("routes", getRoutes());
        root.put("buses", getBuses());
        return root;
    }

    private void createRoute(String busIdParam, String stationIdParam, String duration)
            throws NoEnoughDataException, SQLException {
        try {
            DbConnector.updateRows("INSERT INTO route(bus_id, sort, station_id, duration) VALUES(?,0,?,?)", busIdParam,
                    stationIdParam, duration);
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No data specified");
        }
    }

    private void removeRoute(String idStr) throws NoEnoughDataException, SQLException {
        try {
            long id = Long.valueOf(idStr);
            DbConnector.updateRows("DELETE FROM route WHERE id=?", id);
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No data specified");
        }
    }

    private void moveUp(String routeIdParam, String busIdParam) throws NoEnoughDataException, SQLException {
        try {
            long routeId = Long.parseLong(routeIdParam);
            long busId = Long.parseLong(busIdParam);
            List<BusRoute> routes = getRoutes();

            int index = 0;
            for (BusRoute route : routes) {
                if (route.getId() == routeId) {
                    break;
                }
                index++;
            }
            if (index > 0) {
                Collections.swap(routes, index, --index);

                int sort = 0;
                for (BusRoute route : routes) {
                    DbConnector.updateRows("UPDATE route SET sort=? WHERE id=? AND bus_id=?", sort, route.getId(), busId);
                    sort++;
                }
            }
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No data specified");
        }
    }

    private void moveDown(String routeIdParam, String busIdParam) throws NoEnoughDataException, SQLException {
        try {
            long routeId = Long.parseLong(routeIdParam);
            long busId = Long.parseLong(busIdParam);
            List<BusRoute> routes = getRoutes();

            int index = 0;
            for (BusRoute route : routes) {
                if (route.getId() == routeId) {
                    break;
                }
                index++;
            }
            if (index < routes.size() - 1) {
                Collections.swap(routes, index, ++index);

                int sort = 0;
                for (BusRoute route : routes) {
                    DbConnector.updateRows("UPDATE route SET sort=? WHERE id=? AND bus_id=?", sort, route.getId(), busId);
                    sort++;
                }
            }
        } catch (NullPointerException e) {
            throw new NoEnoughDataException("No data specified");
        }
    }
}
