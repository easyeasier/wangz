package wz.test.servlet;

/**
 * Created by wangz on 17-1-7.
 */
//导入必需的 java 库
import com.google.common.collect.Maps;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

//实现 Filter 类
public class LogFilter implements Filter  {
    static Map<String,String> sessionMap = Maps.newHashMap();

    public void  init(FilterConfig config) throws ServletException {
        // 获取初始化参数
        String site = config.getInitParameter("Site");

        // 输出初始化参数
        System.out.println("网站名称: " + site);
    }

    @Override
    public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        boolean hasSession = false;
        if(session != null){
            String sessionId = (String)session.getAttribute("id");
            if(sessionId != null && sessionMap.get(sessionId) != null){
                hasSession = true;
                System.out.println("doFilter ： 当前用户是:" + sessionMap.get(sessionId) );
            }

        }


       if(hasSession){
           // 把请求传回过滤链
           chain.doFilter(request,response);
       }else{
           ((HttpServletResponse) response).sendRedirect("/pages/login.html");
       }

    }
    public void destroy( ){
		/* 在 Filter 实例被 Web 容器从服务移除之前调用 */
    }
}