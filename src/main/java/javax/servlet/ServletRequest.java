package javax.servlet;
/**
 * 负责封装请求协议
 * @author 
 *
 */
public interface ServletRequest {
	/**
	 * 获取参数值
	 * @param name
	 * @return
	 */
	 String getParameter(String name);
	 /**
	  * 获取参数数组
	  * @param name
	  * @return
	  */
	 String[] getParameters(String name);

	RequestDispatcher getRequestDispatcher(String servletPath);
}
