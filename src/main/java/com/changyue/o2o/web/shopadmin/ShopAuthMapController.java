package com.changyue.o2o.web.shopadmin;

import com.changyue.o2o.dto.ShopAuthMapExecution;
import com.changyue.o2o.dto.UserAccessToken;
import com.changyue.o2o.emums.ShopAuthMapEnum;
import com.changyue.o2o.entity.*;
import com.changyue.o2o.service.PersonInfoService;
import com.changyue.o2o.service.ShopAuthMapService;
import com.changyue.o2o.service.WechatAuthService;
import com.changyue.o2o.util.CodeUtil;
import com.changyue.o2o.util.HttpServletRequestUtil;
import com.changyue.o2o.util.baidu.ShortNetAddress;
import com.changyue.o2o.util.weixin.WechatUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private WechatAuthService wechatAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    private static String urlPrefix;
    private static String urlMiddle;
    private static String urlSuffix;
    private static String authUrl;

    @Value("${weichat.perfix}")
    public void setUrlPrefix(String urlPrefix) {
        ShopAuthMapController.urlPrefix = urlPrefix;
    }

    @Value("${weichat.middle}")
    public void setUrlMiddle(String urlMiddle) {
        ShopAuthMapController.urlMiddle = urlMiddle;
    }

    @Value("${weichat.suffix}")
    public void setUrlSuffix(String urlSuffix) {
        ShopAuthMapController.urlSuffix = urlSuffix;
    }

    @Value("${weicagt.auth.url}")
    public void setAuthUrl(String authUrl) {
        ShopAuthMapController.authUrl = authUrl;
    }

    /**
     * 生成商铺权限的二维码
     *
     * @param request  请求域
     * @param response 响应
     */
    @GetMapping("/generateqrcodeshopauth")
    public void generateQRCodeShopAuth(HttpServletRequest request, HttpServletResponse response) {

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null) {
            try {
                //二维码的有效性 时间
                long timeStamp = System.currentTimeMillis();
                String content = "{aaashopIdaaa:" + currentShop.getShopId() + ",aaacreateTimeaaa:" + timeStamp + "}";
                String lonUrl = urlPrefix + authUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffix;
                String shortUrl = ShortNetAddress.createShortUrl(lonUrl, "1-year");
                BitMatrix qrImg = CodeUtil.generateQRCodeStrem(shortUrl, response);
                //文件流的形式输入到前端
                MatrixToImageWriter.writeToStream(qrImg, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @GetMapping("/addshopauthmap")
    private String addShopAuthMap(HttpServletRequest request, HttpServletResponse response) {
        WechatAuth employeeInfo = getEmployeeInfo(request);

        if (employeeInfo != null) {

            PersonInfo user = personInfoService.getPersonInfoById(employeeInfo.getPersonInfo().getUserId());
            request.getSession().setAttribute("user", user);
            WechatInfo wechatInfo = null;
            try {
                String qrCodeInfo = URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                wechatInfo= mapper.readValue(qrCodeInfo.replace("aaa", "\""), WechatInfo.class);
            } catch (IOException e) {
                return "backstage/operationfail";
            }

            //二维码是否到有效期
            if (!checkQRCodeInfo(wechatInfo)) {
                return "backstage/operationfail";
            }

            //去重校验 避免重复扫二维码
            ShopAuthMapExecution allShopAuthMap = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(), 1, 999);
            List<ShopAuthMap> list = allShopAuthMap.getShopAuthMapPageInfo().getList();
            for (ShopAuthMap shopAuthMap : list) {
                if (shopAuthMap.getEmployee().getUserId().equals(user.getUserId())) {
                    return "backstage/operationfail";
                }
            }

            try {
                ShopAuthMap shopAuthMap = new ShopAuthMap();
                Shop shop = new Shop();
                shop.setShopId(wechatInfo.getShopId());
                shopAuthMap.setShop(shop);
                shopAuthMap.setEmployee(user);
                shopAuthMap.setTitle("员工");
                shopAuthMap.setTitleFlag(1);
                ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService.addShopAuthMap(shopAuthMap);
                if (shopAuthMapExecution.getState() == ShopAuthMapEnum.SUCCESS.getState()) {
                    return "backstage/operationsuccess";
                } else {
                    return "backstage/operationfail";
                }
            } catch (RuntimeException e) {
                return "backstage/operationfail";
            }
        }
        return "backstage/operationfail";
    }

    /**
     * 二维码是否到有效期
     *
     * @param wechatInfo 微信信息
     * @return 是否到期
     */
    private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
        if (wechatInfo != null && wechatInfo.getShopId() != null && wechatInfo.getCreateTime() != null) {
            long nowTime = System.currentTimeMillis();
            return (nowTime - wechatInfo.getCreateTime()) <= 600000;
        } else {
            return false;
        }
    }


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

    @PostMapping("/modifyshopauthmap")
    public Map<String, Object> modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");

<<<<<<< HEAD
        if (statusChange && CodeUtil.checkVerityCode(request)) {
=======
        if (!statusChange && CodeUtil.checkVerityCode(request)) {
>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
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

    /**
     * 通过微信获得员工的信息
     *
     * @param request 请求域
     * @return 携带微信信息的实体
     */
    private WechatAuth getEmployeeInfo(HttpServletRequest request) {

        String code = request.getParameter("code");
        WechatAuth wechatAuth = null;
        if (code != null) {

            UserAccessToken userAccessToken = null;
            try {
                userAccessToken = WechatUtil.getUserAccessToken(code);
                String openId = userAccessToken.getOpenId();
                request.getSession().setAttribute("openId", openId);
                wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wechatAuth;
    }

}
