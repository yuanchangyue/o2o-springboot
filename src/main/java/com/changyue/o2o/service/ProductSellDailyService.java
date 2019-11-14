package com.changyue.o2o.service;

import com.changyue.o2o.entity.ProductSellDaily;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 日销量统计
 * @author: 袁阊越
 * @create: 2019-10-24 14:59
 */
public interface ProductSellDailyService {

    /**
     * 每日定时对所有的商铺的商品销量进行统计
     */
    void dailyCalculate();

    /**
     * 查询日销量的统计列表
     *
     * @param productSellDailyCondition 条件
     * @param beginTime                 开始时间
     * @param endTime                   结束时间
     * @return 统计列表
     */
    List<ProductSellDaily> listProductSellDilly(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime);



}
