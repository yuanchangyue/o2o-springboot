package com.changyue.o2o.service;

import com.changyue.o2o.entity.ShopCategory;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-18 20:49
 */
public interface ShopCategoryService {

    public static final String SHOP_CATEGORY_KEY = "shopCategoryKey";

    /**
     * 获得商铺类别并存放在redis中
     *
     * @param shopCategoryCondition 携带条件的shopCategory 进行查询
     * @return shopCategory 查询商铺类别的集合
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);

}
