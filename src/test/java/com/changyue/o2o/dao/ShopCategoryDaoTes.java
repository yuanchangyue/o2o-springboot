package com.changyue.o2o.dao;

import com.changyue.o2o.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-18 20:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTes {
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategoryDaoTest() {
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(null);
      //  shopCategories.forEach(System.out::println);
    }

}
