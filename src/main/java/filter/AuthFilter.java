package filter;

import services.StaffService;
import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * Created by mvkent on 1/18/2017.
 */
public class AuthFilter extends Filter {
    @Override
    public void handle(Request request, Response response) {
        if(StaffService.getInstance().current(request) == null && request.pathInfo().startsWith("/staff")){
            response.redirect("/auth/login");
        }
    }
}
