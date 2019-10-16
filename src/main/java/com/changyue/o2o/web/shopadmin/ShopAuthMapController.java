package com.changyue.o2o.web.shopadmin;

import com.changyue.o2o.dto.ShopAuthMapExecution;
import com.changyue.o2o.emums.ShopAuthMapEnum;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.ShopAuthMap;
import com.changyue.o2o.service.ShopAuthMapService;
import com.changyue.o2o.util.CodeUtil;
import com.changyue.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: o2o-springboot
 * @description: 商铺授权控制层
 * @author: 袁阊越
 * @create: 2019-10-16 21:56
 */
@RestController
@RequestMapping("/shopadmin")
public class ShopAuthMapController {

    @Autowired
    private ShopAuthMapService shopAuthMapService;

    @GetMapping("/listshopauthmapbyshopid")
    private Map<String, Object> listShopAuthMapByShopId(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<String, Object>();

        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(), pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("shopAuthMapList", shopAuthMapExecution.getShopAuthMapPageInfo());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "pageIndex or pageSize or shopId is empty");
        }
        return modelMap;
    }

    @GetMapping("/getshopauthmapbyid")
    public Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthMapId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        if (shopAuthMapId != null && shopAuthMapId > -1) {
            ShopAuthMap shopAuthMap = shopAuthMapService.queryShopAuthMapByShopAuthId(shopAuthMapId);
            modelMap.put("success", true);
            modelMap.put("shopAuthMap", shopAuthMap);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "shopAuthMapId is empty");
        }
        return modelMap;
    }

    @GetMapping("/modifyshopauthmap")
    public Map<String, Object> modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");

        if (!statusChange && !CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码！");
            return modelMap;
        }

        ObjectMapper mapper = new ObjectMapper();
        ShopAuthMap shopAuthMap = null;

        try {
            shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
            //店家自己不能修改
            if (!checkShopAuthMapPermission(shopAuthMap.getShopAuthId())) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "店家不能最自身操作");
                return modelMap;
            }
            try {
                ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
                if (shopAuthMapExecution.getState() == ShopAuthMapEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "店家不能最自身操作");
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入授权信息");
        }
        return modelMap;
    }

    private boolean checkShopAuthMapPermission(Long shopAuthId) {
        ShopAuthMap shopAuthMap = shopAuthMapService.queryShopAuthMapByShopAuthId(shopAuthId);
        return shopAuthMap.getTitleFlag() != 0;
    }

}
