package com.changyue.o2o.web.local;

import com.changyue.o2o.dto.LocalAuthExecution;
import com.changyue.o2o.emums.LocalAuthStateEnum;
import com.changyue.o2o.entity.LocalAuth;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.service.LocalAuthService;
import com.changyue.o2o.util.CodeUtil;
import com.changyue.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: o2o
 * @description: 平台控制器
 * @author: YuanChangYue
 * @create: 2019-09-25 17:09
 */
@Controller
@RequestMapping(value = "local", method = {RequestMethod.POST, RequestMethod.GET})
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 绑定到本地账号
     *
     * @param request 请求域g
     * @return
     */
    @PostMapping("/bindlocalauth")
    @ResponseBody
    public Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //验证码
        if (CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入错误的验证码");
            return modelMap;
        }

        //取出需要绑定用户的值
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");

        //微信登录的用户
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if (userName != null && password != null && user != null && user.getUserId() != null) {

            LocalAuth localAuth = new LocalAuth();
            localAuth.setPassword(password);
            localAuth.setUsername(userName);
            localAuth.setPersonInfo(user);

            LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
            if (localAuthExecution.getState() == LocalAuthStateEnum.LOCAL_AUTH_CREATE_SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", localAuthExecution.getStateInfo());
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户或密码不能为空");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/changelocalauthpw")
    @ResponseBody
    public Map<String, Object> changeLocalAuthPw(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //验证码
        if (CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入错误的验证码");
            return modelMap;
        }

        //取出需要绑定用户的值
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");

        //微信登录的用户
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

        if (userName != null && password != null && user != null && user.getUserId() != null
                && newPassword != null && !password.equals(newPassword)) {

            try {
                LocalAuth localByUserId = localAuthService.getLocalByUserId(user.getUserId());

                //
                if ((localByUserId == null) || !localByUserId.getUsername().equals(userName)) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的账号非本次登录的账号");
                    return modelMap;
                }

                LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);

                if (localAuthExecution.getState() == LocalAuthStateEnum.LOCAL_AUTH_UPDATE_PASSWORD_SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", localAuthExecution.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/logincheck")
    @ResponseBody
    public Map<String, Object> loginCheck(HttpServletRequest request) {

        Map<String, Object> modelMap = new HashMap<>();

        //密码错误三次重新验证码
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");

        //验证码
        if (needVerify && CodeUtil.checkVerityCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入错误的验证码");
            return modelMap;
        }

        //取出用户的值
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");

        if (userName != null && password != null) {
            LocalAuth localByUserNameAndPw = localAuthService.getLocalByUserNameAndPw(userName, password);
            if (localByUserNameAndPw != null) {
                modelMap.put("success", true);
                request.getSession().setAttribute("user", localByUserNameAndPw.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或者密码错误");
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户或密码不能为空");
            return modelMap;
        }
        return modelMap;
    }

    @PostMapping("/loginout")
    @ResponseBody
    public Map<String, Object> loginOut(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //清空用户
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }

}
