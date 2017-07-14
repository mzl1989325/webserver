package com.mzl.server.core;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/13
 */
public class ResponseObject implements ServletResponse{

    private PrintWriter out;

    public void setWriter(PrintWriter out) {
        this.out = out;
    }

    public PrintWriter getWriter() {
        return out;
    }
}
