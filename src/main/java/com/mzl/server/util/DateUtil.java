package com.mzl.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/12
 */
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm:ss SSS");

    private DateUtil(){

    }

    /**
     * 获取系统当前时间
     * @return 系统当前时间
     */
    public static String getCurrentTime(){
        return sdf.format(new Date());
    }

}
