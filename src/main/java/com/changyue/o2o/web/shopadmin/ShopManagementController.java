package com.changyue.o2o.web.shopadmin;

import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.dto.ShopExecution;
import com.changyue.o2o.emums.ShopStateEnum;
import com.changyue.o2o.entity.*;
import com.changyue.o2o.service.AreaService;
import com.changyue.o2o.service.ProductCategoryService;
import com.changyue.o2o.service.ShopCategoryService;
import com.changyue.o2o.service.ShopService;
import com.changyue.o2o.util.CodeUtil;
import com.changyue.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-17 20:16
 */
@CrossOrigin
@Controller
@RequestMapping(value = "/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 进行商铺管理前需要获得商铺信息
     */
    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/shopping/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", shop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    /**
     * 获得商铺列表
     */
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        String shopName = HttpServletRequestUtil.getString(request, "shopName");

        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            if (shopName != null) {
                shopCondition.setShopName(shopName);
            }
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            //获得商铺列表后将它放进session中,方便权限操作
            request.getSession().setAttribute("shopList", se.getShopList());
            modelMap.put("shopPageInfo", se.getShopPageInfo());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 通过ID获得商铺
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");

        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }

    /**
     * 通过在请求域中获得商铺
     */
    @RequestMapping(value = "/getshopbyrequest", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopByRequest(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (currentShop != null && currentShop.getShopId() != null) {
            try {
                Shop shop = shopService.getByShopId(currentShop.getShopId());
                List<Area> areaList = areaService.getAreaList();
                List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
                modelMap.put("shop", shop);
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }

    /**
     * 获得商铺信息
     */
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            List<Area> areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 注册店铺
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {

        //接收并转化相应参数， 包括店铺信息以及图片
        Map<String, Object> modelMap = new HashMap<>();

        //验证验证码
        if (CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码！");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMeg", "上传图片不能为空");
            return modelMap;
        }

        /*注册店铺*/
        if (shop != null && shopImg != null) {

            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);

            ShopExecution shopExecution = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());

                shopExecution = shopService.addShop(shop, imageHolder);

                if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    //用户可以操作的商铺列表
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<>();
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getState());
                }
                return modelMap;

            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMeg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMeg", "请输入店铺信息！");

            return modelMap;
        }

    }

    /**
     * 修改商铺信息
     */
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {

        //接收并转化相应参数， 包括店铺信息以及图片
        Map<String, Object> modelMap = new HashMap<>();

        //验证验证码
        if (CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码！");
            return modelMap;
        }

        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;

        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());

        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        /*修改店铺*/
        if (shop != null && shop.getShopId() != null) {

            ShopExecution shopExecution = null;
            try {
                if (shopImg == null) {
                    shopExecution = shopService.modifyShop(shop, null);
                } else {
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    shopExecution = shopService.modifyShop(shop, imageHolder);
                }
                if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", shopExecution.getState());
                }
                return modelMap;

            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMeg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMeg", "请输入shopId");
            return modelMap;
        }

    }

    /**
     * 登陆后，获得用户信息
     *
     * @param request 请求域
     * @return 用户信息Map
     */
    @GetMapping("/getuser")
    @ResponseBody
    private Map<String, Object> getUserInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (user != null || user.getUserId() != null) {
            modelMap.put("success", true);
            modelMap.put("user", user);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "尚未登陆");
        }
        return modelMap;
    }
}
