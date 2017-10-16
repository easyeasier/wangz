package wz.test.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangz on 17-1-7.
 */
public class SetErrorCodeServlet extends HttpServlet{

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(404,"ni mei zhaodao");
    }
}
