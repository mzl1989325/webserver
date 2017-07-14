package com.mzl;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/12
 */
public class WebServer {

    //写一个服务器启动方法
    public void serverStart(int port) {

        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (true) {
                Socket socket = serverSocket.accept();//等待连接
                new Processor(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        /**main方法用于生成当前实例并执行start方法
         * 默认是80端口号，如果是通过参数传进来的就是传进来的端口号
         */
        // 通过命令行获取新的端口号
        int port = 80;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        // 生成当前webserver实例执行start方法
        new WebServer().serverStart(port);
    }
}
