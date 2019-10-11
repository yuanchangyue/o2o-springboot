package com.changyue.o2o.util;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-25 18:56
 */
public class PageCalculator {

    /**
     * @param pageIndex 第几页
     * @param pageSize  页数
     * @return 从何开始
     */
    public static int calculator(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }

}
