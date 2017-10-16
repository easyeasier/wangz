package wangz;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangz on 17-1-9.
 */
@WebServlet(name = "IFramecomet",urlPatterns = {"/iframe"})
public class IFramComet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();

        // 由于浏览器原因（FireFox、IE有这个问题，Chrome正常），接收到的数据不会立即输出到页面上。这里首先输入长度大于1024的字符串。
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1024; i++) {
            sb.append('a');
        }
        out.println("<!-- " + sb.toString() + " -->"); // 注意加上HTML注释
        out.flush();

        while(true) {

            // 这里用Thread.sleep来模拟comet，相当于每隔5秒服务器向客户端推送一条消息
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 每隔5秒写一段js到iframe中，去调用parent的addMsg函数，向HTML添加内容
            out.println("<script>parent.addMsg('helloworld<br>')</script>");
            out.flush(); // 这里一定要flush，否则数据不发送
        }
    }

}