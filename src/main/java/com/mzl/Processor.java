package com.mzl;

import java.io.*;
import java.net.Socket;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/12
 */
public class Processor extends Thread{

    private Socket socket;

    private InputStream in;

    private PrintStream out;

    private final static String WEB_ROOT = "E:\\IDE\\idea_workspace\\study2\\webserver\\src\\source";

    public Processor(Socket socket) {

        this.socket = socket;

        try{
            //输入需要输入流
            in = socket.getInputStream();
            //需要输出流
            out = new PrintStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run () {
        System.out.println("解析请求");
        String fileName= parse(in);
        this.sendFile(fileName);
    }


    /**
     * 根据请求输入流，得到访问资源名称
     *
     */
    public String parse(InputStream in) {
        //根据http内容查看用户访问时哪个文件
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String fileName = null;
        try{
            String httpMessage = br.readLine(); //获得http发送请求第一行
            String[] content = httpMessage.split(" ");
            System.out.println(content[1].toString());
            if(content.length !=3) {
                this.sendErrorMessage(400, "client query error");
                return null;
            }
            fileName =content[1]; //文件名是数组第二个值

        }catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;

    }

    /**
     * 资源请求失败时给出提示
     *
     */

    public void sendErrorMessage(int errorCode, String errorMessage) {
        // 以HTTP协议形式打印出去
        out.println("HTTP/1.0 " + errorCode + " " + errorMessage);
        out.println("content-type:text/html");
        out.println();
        out.println("<html>");
        out.println("<title>Error Message");
        out.println("</title>");
        out.println("<body>");
        out.println("<h1>ErrorCode:" + errorCode + ",Message:" + errorMessage
                + "</h1>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
        try{
            in.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendFile(String fileName) {
        //找到文件服务器并返回，将服务器文件读到内存中，然后输出到浏览器

        File file = new File(Processor.WEB_ROOT + fileName);
        if(!file.exists()) {
            sendErrorMessage(404,"File Not Found");
            return;
        }

        try{
            InputStream in = new FileInputStream(file);
            byte content[] = new byte[(int)file.length()];
            in.read(content);

            //以http协议形式打印出去
            out.println("HTTP/1.0 200 queryfile");
            out.println("content-length:" + content.length);
            out.println();
            out.write(content);
            out.flush();
            out.close();
            in.close();

        } catch (Exception e) {
           e.printStackTrace();
        }
    }


}
