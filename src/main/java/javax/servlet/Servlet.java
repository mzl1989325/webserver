package javax.servlet;

import java.io.PrintWriter;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/13
 */
public interface Servlet {

    void service(ServletRequest request,ServletResponse response);
}
