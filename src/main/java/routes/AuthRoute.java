package routes;

import java.io.IOException;
import java.sql.SQLException;
import beans.Staff;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import services.StaffService;
import spark.Request;
import spark.Response;

/**
 * Created by mvkent on 1/18/2017.
 */
public class AuthRoute extends BaseRoute {

    public AuthRoute(String path, Template template) throws IOException {
        super(path, template);
    }

    @Override
    public SimpleHash setResponse(Request request, Response response, SimpleHash root)
            throws IOException, TemplateException, SQLException {
        StaffService staffService = StaffService.getInstance();
        String action = request.params(":do");
        String sessionId = getSessionId(request, response);
        Staff staff = staffService.current(sessionId);
        if(action.equals("login")){
            if (staff == null) {
                try {
                    String login = request.queryParams("login");
                    String password = request.queryParams("pwd");
                    if (login.trim().isEmpty() || password.trim().isEmpty()) {
                        errors.add("Not enough data given");
                    }
                    staff = staffService.login(sessionId, login, password);
                    if(staff != null){
                        response.redirect("/staff");
                    }
                } catch (NullPointerException e) {
                    staffService.drop();
                }
            }
        }else if(action.equals("logout")){
            staffService.logout(sessionId);
            response.redirect("/");
        }
        return root;
    }
}
