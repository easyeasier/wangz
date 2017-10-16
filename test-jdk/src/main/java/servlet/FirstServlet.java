package servlet;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
/**
 * Created by wangz on 16-12-29.
 */
public class FirstServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("gb2312");
        response.setContentType("text/html;charset=utf-8");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");

        PrintStream out = new PrintStream(response.getOutputStream());
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("servlet测试");
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("你的 name ： "+name+"<hr/>");
        out.print("</body>");
        out.println("</html>");
    }
}
