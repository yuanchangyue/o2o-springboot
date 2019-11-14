package com.changyue.o2o.web.weichat;

import com.changyue.o2o.dto.UserAccessToken;
import com.changyue.o2o.dto.WechatAuthExecution;
import com.changyue.o2o.dto.WechatUser;
import com.changyue.o2o.emums.WechatAuthStateEnum;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.WechatAuth;
import com.changyue.o2o.service.PersonInfoService;
import com.changyue.o2o.service.WechatAuthService;
import com.changyue.o2o.util.weixin.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @program: o2o
 * @description: 微信登录
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=
 * wx44de284a108c4d05&redirect_uri=
 * https://changyue.club/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 * <p>
 * 文档地址：https://qydev.weixin.qq.com/wiki/index.php?title=OAuth验证接口
 * @author: YuanChangYue
 * @create: 2019-09-20 20:43
 */
@Controller
@RequestMapping("wechatlogin")
public class WechatLoginController {

    private static Logger log = LoggerFactory.getLogger(WechatLoginController.class);
    private static final String FRONTEND = "1";
    private static final String SHOPEND = "2";

    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private WechatAuthService wechatAuthService;

    @RequestMapping(value = "/logincheck", method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        WechatAuth auth = null;
        if (null != code) {
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }

        //微信账号为空需要注册微信
        if (auth == null) {
            PersonInfo personInfoFromRequest = WechatUtil.getPersonInfoFromRequest(user);
            auth = new WechatAuth();
            auth.setOpenId(openId);
            auth.setCreateTime(new Date());
            if (FRONTEND.equals(roleType)) {
                personInfoFromRequest.setEnableStatus(1);
            } else {
                personInfoFromRequest.setEnableStatus(2);
            }
            auth.setPersonInfo(personInfoFromRequest);
            WechatAuthExecution wechatAuthExecution = wechatAuthService.registerWechat(auth);
            if (wechatAuthExecution.getState() != WechatAuthStateEnum.WECHAT_AUTH_SUCCESS.getState()) {
                return null;
            } else {
                personInfoFromRequest = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user", personInfoFromRequest);
            }
        }

        if (FRONTEND.equals(roleType)) {
            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
            return "/frontend/index";
        } else {
            return "/backstage/index";
        }

    }

}
