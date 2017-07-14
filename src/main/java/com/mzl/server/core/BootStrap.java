package com.mzl.server.core;

import com.mzl.server.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * server 启动入口
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/12
 */
public class BootStrap {

    public static void main(String[] args) {
        start();
    }

    public static void start() {

        long begin = System.currentTimeMillis();
        Logger.log("server start");
        ServerSocket serverSocket = null;

        try{

            int port = ServerParse.getPort();
            serverSocket = new ServerSocket(port);
            WebParse.parse(new String[]{"oa"});
            long end = System.currentTimeMillis();
            Logger.log("server started, "+(end-begin)+"ms");

            //服务器启动成功.执行到一下代码进入等待状态 等待浏览器客户端请求
            while(true) {
                Socket cilentSocket = serverSocket.accept();
//                InputStream in = cilentServer.getInputStream();
//                byte[] bytes = new byte[in.available()];
//                int readNum = in.read(bytes);
//                Logger.log("readNum:"+readNum);
//                String requstMsg = new String(bytes);
//                Logger.log("requstMsg:"+requstMsg);
                Thread handlerRequestThread = new Thread(new HandlerRequest(cilentSocket));
                Logger.log(handlerRequestThread + " begin handler request");
                handlerRequestThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(serverSocket !=null && !serverSocket.isClosed()) {
                try{
                    serverSocket.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
