package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.ProductSellDailyDao;
import com.changyue.o2o.entity.ProductSellDaily;
import com.changyue.o2o.service.ProductSellDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 每日统计销量service实现类
 * @author: 袁阊越
 * @create: 2019-10-24 15:01
 */
@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
    private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Override
    public void dailyCalculate() {
        log.info("Quartz Running!!");

        productSellDailyDao.insertProductSellDaily();
        //添加销量为零的商品的日销量
        productSellDailyDao.insertNullProductSellDaily();

    }

    @Override
    public List<ProductSellDaily> listProductSellDilly(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime) {
        return productSellDailyDao.queryProductSellDaily(productSellDailyCondition, beginTime, endTime);
    }

}
