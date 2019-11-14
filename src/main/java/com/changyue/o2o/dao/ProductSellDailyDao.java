package com.changyue.o2o.dao;

import com.changyue.o2o.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 袁阊越
 * @Title: ProductSellDailyDao
 * @Package com.changyue.o2o.dao
 * @Description: 日销量的数据访问层
 * @date 2019/10/15/015
 */
@Repository
public interface ProductSellDailyDao {

    /**
     * 平台全部的日销量
     *
     * @return 影响行数
     */
    int insertProductSellDaily();


    /**
     * 查询商品的日销量统计列表
     *
     * @param productSellDaily 条件
     * @param beginDate        开始时间
     * @param endDate          结束时间
     * @return 商品的日销量统计列表
     */
    List<ProductSellDaily> queryProductSellDaily(@Param("productSellDailyCondition") ProductSellDaily productSellDaily, @Param("beginTime") Date beginDate, @Param("endTime") Date endDate);


    /**
     * 统计平台当天没有销量的商品
     * @return 影响行数
     */
    int insertNullProductSellDaily();

}