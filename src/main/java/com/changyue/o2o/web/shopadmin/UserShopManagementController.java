package com.changyue.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.changyue.o2o.dto.UserShopMapExecution;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.UserShopMap;
import com.changyue.o2o.service.UserShopMapService;
import com.changyue.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shopadmin")
public class UserShopManagementController {

    @Autowired
    private UserShopMapService userShopMapService;

    @RequestMapping(value = "/listusershopmapsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserShopMapsByShop(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
                && (currentShop.getShopId() != null)) {
            UserShopMap userShopMapCondition = new UserShopMap();
            userShopMapCondition.setShop(currentShop);

            String userName = HttpServletRequestUtil.getString(request,
                    "userName");

            if (userName != null) {
                PersonInfo user = new PersonInfo();
                user.setName(userName);
                userShopMapCondition.setUser(user);
            }
            UserShopMapExecution ue = userShopMapService.listUserShopMap(
                    userShopMapCondition, pageIndex, pageSize);

            modelMap.put("userShopMapPageInfo", ue.getUserShopMapPageInfo());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

}
