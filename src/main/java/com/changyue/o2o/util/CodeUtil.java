package com.changyue.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-18 22:01
 */
public class CodeUtil {

    public static boolean checkVerityCode(HttpServletRequest request) {

        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");

        return verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected);
    }
}
