package com.changyue.o2o.util;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 22:43
 */

/*<Context docBase="/Users/baidu/work/image/upload" path="/unload" />*/
public class PathUtils {
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = null;
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/projectdev/img/";
        } else {
            basePath = "/Users/baidu/work/image";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }

}
