package com.changyue.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 22:43
 */
@Component
public class PathUtils {

    private static String linuxPath;

    private static String winPath;

    private static String shopPath;

    @Value("${liunx.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtils.linuxPath = linuxPath;
    }

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtils.winPath = winPath;
    }

    @Value("${shop.base.path}")
    public void setShopPath(String shopPath) {
        PathUtils.shopPath = shopPath;
    }

    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = null;
        if (os.toLowerCase().startsWith("win")) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = shopPath + shopId + separator;
        return imagePath.replace("/", separator);
    }

}
