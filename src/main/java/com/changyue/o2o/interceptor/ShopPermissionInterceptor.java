package com.changyue.o2o.interceptor;

import com.changyue.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: o2o
 * @description: 商铺权限管理拦击器
 * @author: YuanChangYue
 * @create: 2019-09-26 22:37
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");

        //当前商铺是否在商铺列表中
        if (currentShop != null && shopList != null) {
            for (Shop shop : shopList) {
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        //禁止进入controller
        return false;
    }
}
