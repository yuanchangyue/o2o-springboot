package com.changyue.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: o2o
 * @description: 前端页面控制器
 * @author: YuanChangYue
 * @create: 2019-08-22 09:32
 */
@Controller
@RequestMapping("/frontend")
public class FrontendPageController {

    /**
     * 前端商铺列表
     */
    @GetMapping(value = "shoplist")
    public String shopList() {
        System.out.println("come in");
        return "frontend/frontendshoplist";
    }

    /**
     * 前端商铺详情
     */
    @RequestMapping(value = "/shopdetail")
    public String shopDetail() {
        return "frontend/shopdetail";
    }

    /**
     * 产品搜索
     */
    @RequestMapping(value = "/productsearch")
    public String productSearch() {
        return "frontend/productsearch";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "frontend/index";
    }

    /**
     * 商品详情页
     */
    @RequestMapping(value = "/productdetail")
    public String productDetail() {
        return "frontend/productdetail";
    }

}
