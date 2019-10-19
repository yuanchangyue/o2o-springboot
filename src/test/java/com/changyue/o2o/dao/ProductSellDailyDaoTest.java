package com.changyue.o2o.dao;

import com.changyue.o2o.entity.ProductSellDaily;
import com.changyue.o2o.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: o2o-springboot
 * @description:
 * @author: 袁阊越
 * @create: 2019-10-15 22:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyDaoTest {
    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Test
    public void testInsert() {
        int i = productSellDailyDao.insertProductSellDaily();
        System.out.println(i);
    }

    @Test
    public void testQuery() {
        ProductSellDaily productSellDaily = new ProductSellDaily();
        Shop shop = new Shop();
        shop.setShopId(19L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> productSellDailies = productSellDailyDao.queryProductSellDaily(productSellDaily, null, null);
        productSellDailies.forEach(System.out::println);

    }
}
