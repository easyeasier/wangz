package wangz;

import com.google.common.base.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangz on 17-1-8.
 */
public class LoginServlet extends HttpServlet {
    static int id = 1;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if (Strings.isNullOrEmpty(name)) {
            throw new RuntimeException("登录名不能空");
        }

        String sessionId = (id++) + "";
        HttpSession session = request.getSession(true);
        if (session.isNew()) {
            System.out.println("session id 是：" + session.getId());
            System.out.println("requestedSession id 是: " + request.getRequestedSessionId());
            session.setAttribute("id", sessionId);
            LogFilter.sessionMap.put(sessionId, name);
        }

        PrintWriter out = response.getWriter();
        out.println("<!docType html>\n" +
                "<head><title>" + "welcome" + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "welcome" +name+"\n"+
                "</body></html>");
    }
}
