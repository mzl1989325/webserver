package com.mzl.server.core;

import com.mzl.server.util.Logger;
import com.mzl.server.util.ServletCache;
import org.mzl.servlet.LoginServlet;

import javax.servlet.Servlet;
import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/12
 */
public class HandlerRequest implements Runnable{

    private Socket clientSocket;

    public HandlerRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        Logger.log(Thread.currentThread().getName()+" handler request!");
        BufferedReader br = null;
        PrintWriter out = null;
        //处理请求
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String readLine = br.readLine();
            String requestURI = readLine.split(" ")[1];
            Logger.log("readLine: "+"["+readLine+"]"+"requestURI "+"["+requestURI+"]");

            out = new PrintWriter(clientSocket.getOutputStream());

            if (requestURI.trim().endsWith(".html") || requestURI.trim().endsWith(".htm")) {
                responseStaticPage(requestURI,out);
            } else {
                String servletPath = requestURI;
                if(servletPath.contains("?")) {
                    servletPath = servletPath.split("\\?")[0];
                }
                String webAppName = servletPath.split("/")[1];//oa
                Map<String,String> servletMap = WebParse.servletMaps.get(webAppName);
                String urlPattern =servletPath.substring(webAppName.length()+1);//login
                String servletClassName = servletMap.get(urlPattern);

                if(servletClassName !=null){

                    Servlet servlet = ServletCache.get(urlPattern);

                    if(servlet==null){
                        Class c = Class.forName(servletClassName);
                        servlet =  (Servlet)c.newInstance();
                        ServletCache.put(urlPattern, servlet);
                    }
                    Logger.log("获取到的servlet对象为："+servlet);
                    out.append("HTTP/1.1 404 OK\n");
                    out.append("Content-Type: text/html;charset=UTF-8\n\n");
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setWriter(out);
                    RequestObject requestObject = new RequestObject(requestURI);
                    servlet.service(requestObject,responseObject);
                } else {
                    StringBuilder html = new StringBuilder();
                    html.append("HTTP/1.1 404 OK\n");
                    html.append("Content-Type: text/html;charset=UTF-8\n\n");
                    html.append("<html>");
                    html.append("<head>");
                    html.append("<title>404-Not Found</title>");
                    html.append("</head>");
                    html.append("<body>");
                    html.append("<h1 align='center'><font color='red'>404-Not Found!</font></h1>");
                    html.append("</body>");
                    html.append("</html>");
                    out.print(html);
                }


            }
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {

            if( out != null ) {
                out.close();
            }

            if(br !=null) {
                try{
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(clientSocket != null && !clientSocket.isClosed()){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void responseStaticPage(String resuestURI,PrintWriter out) {
        String htmlFilePath = resuestURI.substring(1);
        BufferedReader br =null;
        Logger.log(htmlFilePath);

        try {

            br = new BufferedReader(new FileReader(htmlFilePath));
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 200 OK\\n");
            html.append("Content-Type: text/html;charset=UTF-8\n\n");
            String temp = null;
            while((temp = br.readLine())!=null) {
                html.append(temp);
            }
            out.println(html);

        }catch (Exception e) {
            //返回错误提示
            //报404错误
            StringBuilder html404 = new StringBuilder();
            html404.append("HTTP/1.1 404 OK\n");
            html404.append("Content-Type: text/html;charset=UTF-8\n\n");
            html404.append("<html>");
            html404.append("<head>");
            html404.append("<title>404-Not Found</title>");
            html404.append("</head>");
            html404.append("<body>");
            html404.append("<h1 align='center'><font color='red'>404-Not Found!</font></h1>");
            html404.append("</body>");
            html404.append("</html>");
            out.print(html404.toString());

        }finally {
            if(br !=null){
                try {
                    br.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
