package com.mzl.server.util;
/**
 * 日志记录器
 * @author yxm
 * @version
 * @since
 */
public class Logger {
   /**
    * 1.工具类中的方法一般都是静态的，不需要创建对象，直接使用类调用
    * 2.所以工具类中的方法一般都是私有的，但不是必须的
    */
	public Logger(){
		
	}
	/**
	 * 记录日志消息
	 */
	public static void log(String info){
		 System.out.println("[INFO]"+DateUtil.getCurrentTime()+" "+info);
	}
}
