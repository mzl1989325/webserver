package com.mzl.server.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

/**
 * Servlet对象缓存池
 * @author muzonglin
 *
 */
public class ServletCache {

   private static Map<String,Servlet> servletMap = new HashMap<String,Servlet>();
   
   public static Servlet get(String name){
	   return servletMap.get(name);
   }
   
   public static void put(String urlPattern,Servlet servlet){
	   servletMap.put(urlPattern,servlet);
   }
}
