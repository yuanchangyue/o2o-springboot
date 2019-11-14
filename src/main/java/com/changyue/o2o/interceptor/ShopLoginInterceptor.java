package com.changyue.o2o.interceptor;

import com.changyue.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @program: o2o
 * @description: 商铺管理系统登录拦截器
 * @author: YuanChangYue
 * @create: 2019-09-26 22:17
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 进入之前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object userObj = request.getSession().getAttribute("user");

        if (userObj != null) {
            PersonInfo user = (PersonInfo) userObj;
            if (user != null && user.getUserId() != null && user.getEnableStatus() == 1) {
                return true;
            }
        }

        //没有登录,跳转到登录页
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<script>");
        writer.println("window.open('" + request.getContextPath() + "/local/login?usertype=2','_self')");
        writer.println("</script>");
        writer.println("</html>");

        return false;
    }
}
