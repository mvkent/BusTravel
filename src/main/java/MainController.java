import java.io.IOException;
import java.sql.SQLException;
import filter.AuthFilter;
import freemarker.template.Configuration;
import routes.AuthRoute;
import routes.ClientRoutes;
import routes.MainPageRoute;
import routes.StaffBuses;
import routes.StaffRoute;
import routes.StaffRoutes;
import routes.StaffStations;
import spark.Spark;
import utils.DbConnector;

import static spark.Spark.before;
import static spark.Spark.setPort;

public class MainController {
    private static Configuration configuration;

    public static void main(String[] args) throws SQLException, IOException {
        DbConnector.createTables();
        new MainController();
    }

    public MainController() throws IOException, SQLException {
        createFreemarkerConfiguration();
        setPort(8083);
        Spark.staticFileLocation("/assets");
        initializeRoutes();
    }

    private Configuration createFreemarkerConfiguration() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(MainController.class, "/freemarker");
        return configuration;
    }

    private void initializeRoutes() throws IOException {
        before(new AuthFilter());

        new MainPageRoute("/", configuration.getTemplate("index.ftl"));
        new StaffRoute("/staff", configuration.getTemplate("staff/index.ftl"));
        new StaffStations("/staff/stations", configuration.getTemplate("staff/stations.ftl"));
        new StaffBuses("/staff/buses", configuration.getTemplate("staff/buses.ftl"));
        new StaffRoutes("/staff/routes", configuration.getTemplate("staff/routes.ftl"));
        new AuthRoute("/auth/:do", configuration.getTemplate("auth.ftl"));

        new ClientRoutes("/client", configuration.getTemplate("client/index.ftl"));
    }
}