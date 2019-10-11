package com.changyue.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-17 23:16
 */
@Controller
@RequestMapping(value = "/shopadmin", method = RequestMethod.GET)
public class ShopAdminPageController {

    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        return "backstage/shopoperation";
    }

    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "backstage/index";
    }

    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement", method = RequestMethod.GET)
    public String productCategoryManagement() {
        return "backstage/productcategory";
    }

    @RequestMapping(value = "/productoperation")
    public String productOperation() {
        return "backstage/productoperation";
    }

    @RequestMapping(value = "/productlist")
    public String productList() {
        return "backstage/productlist";
    }

}

