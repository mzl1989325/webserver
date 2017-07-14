package javax.servlet;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/13
 */
public interface RequestDispatcher {

    void forward(ServletRequest request,ServletResponse response);
}
