package wz.test.comet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangz on 17-1-9.
 */
@WebServlet(name = "LongpollingServlet", urlPatterns = {"/longpolling"})
public class LongpollingServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        System.out.println("========"+request.getRequestedSessionId());
        int i= 1;
        // 这里用Thread.sleep来模拟comet，相当于每隔5秒服务器向客户端推送一条消息
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.println("helloworld<br>" + i);
    }

}