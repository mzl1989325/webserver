package org.mzl.servlet;

import com.mzl.server.util.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/13
 */
public class LoginServlet implements Servlet{

    public  void service(ServletRequest request, ServletResponse response) {
        PrintWriter out = response.getWriter();
//        Logger.log("正在身份验证，请稍后");
//        out.append("<html>");
//        out.append("<head>");
//        out.append("<meta charset=\"utf-8\">");
//        out.append("<title>测试</title>");
//        out.append("</head>");
//        out.append("<body>");
//        out.append("<h1 align='center'><font color='blue'>正在验证，请稍后......</font></h1>");
//        out.append("</body>");
//        out.append("</html>");
        request.getRequestDispatcher("/oa/list").forward(request,response);
    }
}
