package org.mzl.servlet;

import com.mzl.server.util.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/13
 */
public class ListServlet implements Servlet{

    public void service(ServletRequest request, ServletResponse response) {
        Logger.log("ListServlet service execute");

    }
}
