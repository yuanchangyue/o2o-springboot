package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-18 20:21
 */
public class ShopCategoryDaoTes extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategoryDaoTest() {
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(null);
      //  shopCategories.forEach(System.out::println);
    }

}
