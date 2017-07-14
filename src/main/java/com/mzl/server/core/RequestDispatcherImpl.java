package com.mzl.server.core;

import com.mzl.server.util.Logger;
import com.mzl.server.util.ServletCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/13
 */
public class RequestDispatcherImpl implements RequestDispatcher {

    private String uri;

    public RequestDispatcherImpl(String uri) {
        this.uri=uri;
    }

    public void forward(ServletRequest request, ServletResponse response) {

        if(uri.endsWith(".html") || uri.endsWith(".htm")) {
            String htmlPath = uri.substring(1);
            Logger.log(htmlPath);
            BufferedReader br = null;

            try {
                br = new BufferedReader(new FileReader(htmlPath));
                String temp = null;
                while((temp = br.readLine()) != null ) {
                    response.getWriter().print(temp);
                }

            }catch (Exception e) {
                e.printStackTrace();
            } finally {

                if(br!=null) {
                    try{
                        br.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

            }
        } else {
            String s1 = uri.substring(1);
            Logger.log(s1);
            String webappname = "";
            if(s1.contains("/")){
                webappname =s1.split("/")[0];
            }else {
                webappname=s1;
            }
            String servletPath = s1.substring(webappname.length());
            if(servletPath.length()>0){
                String servletName = WebParse.servletMaps.get(webappname).get(servletPath);
                Servlet servlet = ServletCache.get(servletName);
                if(servlet == null){
                    try {
                        servlet = (Servlet)Class.forName(servletName).newInstance();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                servlet.service(request, response);
            }

        }

    }
}
