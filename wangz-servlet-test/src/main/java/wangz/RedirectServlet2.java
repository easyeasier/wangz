package wangz;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangz on 17-1-7.
 */
public class RedirectServlet2 extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendRedirect("/pages/a.html");
        String value = getServletConfig().getInitParameter("abc");
        System.out.println("============="+value);
    }
}
