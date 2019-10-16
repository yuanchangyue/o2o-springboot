package com.changyue.o2o.util;

import com.google.code.kaptcha.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.font.LayoutPathImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-18 22:01
 */
public class CodeUtil {

    /**
     * 检验前端的验证码
     *
     * @param request 请求域
     * @return 是否通过
     */
    public static boolean checkVerityCode(HttpServletRequest request) {

        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");

        return verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected);
    }


    /**
     * 生成二维码
     *
     * @param content  内容
     * @param response http响应
     * @return bitMatrix
     */
    public static BitMatrix generateQRCodeStrem(String content, HttpServletResponse response) {
        //浏览器不用缓存二维码
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-store");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        //设置图片的文字编辑以及内边
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //内容编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 0);
        int width = 300;
        int height = 300;
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }

}
