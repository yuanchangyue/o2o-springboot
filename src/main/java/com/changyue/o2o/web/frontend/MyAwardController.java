package com.changyue.o2o.web.frontend;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changyue.o2o.dto.UserAwardMapExecution;
import com.changyue.o2o.emums.UserAwardMapStateEnum;
import com.changyue.o2o.entity.Award;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.UserAwardMap;
import com.changyue.o2o.service.AwardService;
import com.changyue.o2o.service.PersonInfoService;
import com.changyue.o2o.service.UserAwardMapService;
import com.changyue.o2o.util.HttpServletRequestUtil;
import com.changyue.o2o.util.baidu.ShortNetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


@Controller
@RequestMapping("/frontend")
public class MyAwardController {
    @Autowired
    private UserAwardMapService userAwardMapService;
    @Autowired
    private AwardService awardService;
    @Autowired
    private PersonInfoService personInfoService;
    private static String URLPREFIX = "https://open.weixin.qq.com/connect/oauth2/authorize?"
            + "appid=wxd7f6c5b8899fba83&"
            + "redirect_uri=115.28.159.6/myo2o/shop/exchangeaward&"
            + "response_type=code&scope=snsapi_userinfo&state=";
    private static String URLSUFFIX = "#wechat_redirect";

    @RequestMapping(value = "/listuserawardmapsbycustomer", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserAwardMapsByCustomer(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if ((pageIndex > -1) && (pageSize > -1) && (user != null) && (user.getUserId() != null)) {
            UserAwardMap userAwardMapCondition = new UserAwardMap();
            userAwardMapCondition.setUser(user);
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId > -1) {
                Shop shop = new Shop();
                shop.setShopId(shopId);
                userAwardMapCondition.setShop(shop);
            }
            String awardName = HttpServletRequestUtil.getString(request,
                    "userName");
            if (awardName != null) {
                Award award = new Award();
                award.setAwardName(awardName);
                userAwardMapCondition.setAward(award);
            }
            UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(
                    userAwardMapCondition, pageIndex, pageSize);
            modelMap.put("userAwardMapPageInfo", ue.getUserAwardMapPageInfo());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or userId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/adduserawardmap", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addUserAwardMap(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = (PersonInfo) request.getSession()
                .getAttribute("user");
        Long awardId = HttpServletRequestUtil.getLong(request, "awardId");
        UserAwardMap userAwardMap = compactUserAwardMap4Add(user, awardId);
        if (userAwardMap != null) {
            try {
                UserAwardMapExecution se = userAwardMapService
                        .addUserAwardMap(userAwardMap);
                if (se.getState() == UserAwardMapStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请选择领取的奖品");
        }
        return modelMap;
    }
/*
	@RequestMapping(value = "/generateqrcode4award", method = RequestMethod.GET)
	@ResponseBody
	private void generateQRCode4Product(HttpServletRequest request,
			HttpServletResponse response) {
		long userAwardId = HttpServletRequestUtil.getLong(request,
				"userAwardId");
		UserAwardMap userAwardMap = userAwardMapService
				.getUserAwardMapById(userAwardId);
		PersonInfo user = (PersonInfo) request.getSession()
				.getAttribute("user");
		if (userAwardMap != null && user != null && user.getUserId() != null
				&& userAwardMap.getUserId() == user.getUserId()
				&& userAwardMap.getUsedStatus() == 0) {
			long timpStamp = System.currentTimeMillis();
			String content = "{\"userAwardId\":" + userAwardId
					+ ",\"customerId\":" + user.getUserId()
					+ ",\"createTime\":" + timpStamp + "}";
			String longUrl = URLPREFIX + content + URLSUFFIX;
			String shortUrl = ShortNetAddress.getShortURL(longUrl);
			BitMatrix qRcodeImg = QRCodeUtil.generateQRCodeStream(shortUrl,
					response);
			try {
				MatrixToImageWriter.writeToStream(qRcodeImg, "png",
						response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

    private UserAwardMap compactUserAwardMap4Add(PersonInfo user, Long awardId) {
        UserAwardMap userAwardMap = null;
        if (user != null && user.getUserId() != null && awardId != -1) {
            userAwardMap = new UserAwardMap();
            PersonInfo personInfo = personInfoService.getPersonInfoById(user
                    .getUserId());
            Award award = awardService.getAwardById(awardId);

            userAwardMap.setUser(personInfo);
            userAwardMap.setAward(award);
            Shop shop = new Shop();
            shop.setShopId(award.getShopId());
            userAwardMap.setShop(shop);
            userAwardMap.setPoint(award.getPoint());
            userAwardMap.setCreateTime(new Date());
            userAwardMap.setUsedStatus(1);
        }
        return userAwardMap;
    }
}
