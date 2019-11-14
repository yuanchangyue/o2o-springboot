package com.changyue.o2o.service;

import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.dto.ShopExecution;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.exceptions.ShopOperationException;


/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-17 18:34
 */

public interface ShopService {

    /**
     * 获得Shop的分页数据
     */
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 通过shopId获得商铺
     *
     * @param shopId shopId
     * @return 商铺
     */
    Shop getByShopId(long shopId);

    /**
     * 修改商铺信息
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 添加商铺
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
