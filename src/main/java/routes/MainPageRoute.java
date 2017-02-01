package routes;

import java.io.IOException;
import java.sql.SQLException;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;

/**
 * Created by mvkent on 1/18/2017.
 */
public class MainPageRoute extends BaseRoute {

    public MainPageRoute(String path, Template template) throws IOException {
        super(path, template);
    }

    @Override
    public SimpleHash setResponse(Request request, Response response, SimpleHash root) throws IOException, TemplateException,
            SQLException {
        return root;
    }
}
