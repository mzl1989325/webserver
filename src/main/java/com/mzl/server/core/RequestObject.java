package com.mzl.server.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;

/**
 * 负责封装请求协议的request对象
 * @author yxm
 *
 */
public class RequestObject implements ServletRequest{
   private  Map<String,String[]> paramMap = new HashMap<String,String[]>();
   
   public RequestObject(String requestURIAndData){
	    //requestURIAndData可能是：
	    // /oa/save
	    // /oa/save?username=zs&sex=m
	   //  /oa/save?username=zs&sex=m&interest=sport
	   //  /oa/save?username=zs&sex=m&interest=sport$interest=music
	   
	   //解析uri和data
	   if(requestURIAndData.contains("?")){
		   String[] uriAndData = requestURIAndData.split("\\?");
		   System.out.println(uriAndData);
		   if(uriAndData.length>=2){
			    // username= 
			    // username=zs&sex=m
			    //  username=zs&sex=m&interest=sport
			   //  username=zs&sex=m&interest=sport$interest=music
			   String data = uriAndData[1];
			   if(data.contains("&")){
				      String[] nameAndValues = data.split("&");
				      for (String nameAndValue : nameAndValues) {
				    	  String[] nameAndValueArr = nameAndValue.split("=");
				    	  if(paramMap.containsKey(nameAndValueArr[0])){//如果包含某一个参数,扩展数组
				    		  String[] values = paramMap.get(nameAndValueArr[0]);
				    		  String[] newValues =  new String[values.length+1];
				    		  System.arraycopy(values, 0, newValues, 0, values.length);
				    		  if(nameAndValueArr.length>1){
				    			  newValues[newValues.length-1] = nameAndValueArr[1];
				    		  }else{
				    			  newValues[newValues.length-1] = "";
				    		  }
				    		  paramMap.put(nameAndValueArr[0], newValues);
				    	  }else{
				    		  if(nameAndValueArr.length>1){
				    			  paramMap.put(nameAndValueArr[0], new String[]{nameAndValueArr[1]});
				    		  }else{
				    			  paramMap.put(nameAndValueArr[0],new String[]{""});
				    		  }
				    	  }
					}
			   }else{
				   String[] strs = data.split("=");
				   if(strs.length>1){
					   paramMap.put(strs[0],new String[]{strs[1]});
				   }else{
					   paramMap.put(strs[0],new String[]{""});
					   
				   }
			   }
			  
		   }
	   }
   }
   public String getParameter(String name){
	   String[] parameterVaues = paramMap.get(name);
	   return (parameterVaues != null && parameterVaues.length !=0)?parameterVaues[0]: null;
   }
   public String[] getParameters(String name){
	   return paramMap.get(name);
   }

	public RequestDispatcher getRequestDispatcher(String servletPath) {
		return new RequestDispatcherImpl(servletPath);
	}
}








