package com.changyue.o2o.dao;

import com.changyue.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-13 01:25
 */
public interface ShopDao {

    /**
     * @return queryShopList的总数
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 分页
     *
     * @param shopCondition shop
     * @param rowIndex      第几行开始取
     * @param pageSize      返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 分页 交给pageHelper
     *
     * @return
     */
    List<Shop> queryShopListByAll(@Param("shopCondition") Shop shopCondition);

    /**
     * 通过商铺的id查询
     *
     * @param
     * @return
     */
    Shop queryById(Long shopId);

    /**
     * 新增商店
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
