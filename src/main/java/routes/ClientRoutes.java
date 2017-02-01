package routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import beans.Bus;
import beans.BusRoute;
import beans.Station;
import exceptions.NoEnoughDataException;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

/**
 * Created by mvkent on 1/18/2017.
 */
public class ClientRoutes extends BaseRoute {

    public ClientRoutes(String path, Template template) throws IOException {
        super(path, template);
    }

    @Override
    public SimpleHash setResponse(Request request, Response response, SimpleHash root)
            throws IOException, TemplateException, SQLException, NoEnoughDataException {

        long fromId = 0L;
        long toId = 0L;
        String from = request.queryParams("from");
        if (from != null) {
            fromId = Long.parseLong(from);
        }
        String to = request.queryParams("to");
        if (to != null) {
            toId = Long.parseLong(to);
        }

        root.put("from", fromId);
        root.put("to", toId);

        List<Bus> buses = getBuses();
        List<Station> stations = getStations();
        List<BusRoute> routes = getRoutes();

        buses = filterBusesByRoutes(buses, routes, fromId, toId);

        root.put("stations", stations);
        root.put("buses", buses);
        root.put("routes", routes);
        root.put("page", "client");
        return root;
    }

    private List<Bus> filterBusesByRoutes(List<Bus> buses, List<BusRoute> routes, long from, long to) {
        Iterator<BusRoute> busRouteIterator = routes.iterator();
        List<Long> filteredBussesIds = new ArrayList<>(routes.size());
        int lastSort = 0;
        long lastApprowedBus = 0;
        while (busRouteIterator.hasNext()) {
            BusRoute busRoute = busRouteIterator.next();
            long currentBus = busRoute.getBus().getId();
            if (busRoute.getStation().getId() == from) {
                lastApprowedBus = currentBus;
                lastSort = busRoute.getSort();
                busRoute.setSelected(true);
            }
            if (busRoute.getStation().getId() == to && busRoute.getSort() > lastSort && lastApprowedBus == currentBus) {
                filteredBussesIds.add(busRoute.getBus().getId());
                busRoute.setSelected(true);
            }
        }

        Iterator<Bus> busIterator = buses.iterator();
        while (busIterator.hasNext()) {
            if (!filteredBussesIds.contains(busIterator.next().getId())) {
                busIterator.remove();
            }
        }
        return buses;
    }
}
