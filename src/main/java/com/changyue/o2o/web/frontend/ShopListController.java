package com.changyue.o2o.web.frontend;

import com.changyue.o2o.dto.ShopExecution;
import com.changyue.o2o.entity.Area;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.ShopCategory;
import com.changyue.o2o.service.AreaService;
import com.changyue.o2o.service.ShopCategoryService;
import com.changyue.o2o.service.ShopService;
import com.changyue.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: o2o
 * @description: 商铺列表控制器
 * @author: YuanChangYue
 * @create: 2019-08-21 14:10
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    AreaService areaService;

    @Autowired
    ShopService shopService;

    @Autowired
    ShopCategoryService shopCategoryService;

    @GetMapping("/listshopspageinfo")
    @ResponseBody
    public Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        long parentId = HttpServletRequestUtil.getLong(request, "parentId");

        List<ShopCategory> shopCategoryList = null;

        if (parentId != -1) {
            try {
                ShopCategory shopCategory = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategory.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategory);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }

        modelMap.put("shopCategoryList", shopCategoryList);

        try {
            List<Area> areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", true);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }

    /**
     * 查询商铺的列表
     *
     * @param request 请求域
     * @return modelMap
     */
    @GetMapping("/listshops")
    @ResponseBody
    public Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        if ((pageIndex > -1) && (pageSize > -1)) {

            //一级列表的id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            //二级列表的id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            //区域信息id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //商铺Id
            String shopName = HttpServletRequestUtil.getString(request, "shopName");

            Shop shop = compactShopSearchCondition(parentId, shopCategoryId, areaId, shopName);

            //查询商铺列表
            ShopExecution se = shopService.getShopList(shop, pageIndex, pageSize);
            modelMap.put("shopPageInfo", se.getShopPageInfo());
            modelMap.put("success", true);

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "pageIndex or pageSize is empty! ");
        }

        return modelMap;
    }

    /**
     * 整合需要查询的条件
     *
     * @param parentId       一级列表的id
     * @param shopCategoryId 二级列表的id
     * @param areaId         区域信息id
     * @param shopName       商铺Id
     * @return 查询携带条件的商铺信息
     */
    private Shop compactShopSearchCondition(long parentId, long shopCategoryId, int areaId, String shopName) {

        Shop conditionShop = new Shop();
        if (parentId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            shopCategory.setParent(parent);
            conditionShop.setShopCategory(shopCategory);
        }

        if (shopCategoryId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            conditionShop.setShopCategory(shopCategory);
        }

        if (areaId != -1L) {
            Area area = new Area();
            area.setAreaId(areaId);
            conditionShop.setArea(area);
        }

        if (shopName != null) {
            conditionShop.setShopName(shopName);
        }

        conditionShop.setEnableStatus(1);

        return conditionShop;
    }


}
