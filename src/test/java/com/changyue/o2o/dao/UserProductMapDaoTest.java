package com.changyue.o2o.dao;

import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Product;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.UserProductMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description:
 * @author: 袁阊越
 * @create: 2019-10-15 19:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProductMapDaoTest {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Test
    public void testInsertUserProductMap() {

        UserProductMap userProductMap = new UserProductMap();
        Shop shop = new Shop();
        PersonInfo user = new PersonInfo();
        PersonInfo operator = new PersonInfo();
        Product product = new Product();
        shop.setShopId(19L);
        user.setUserId(13L);
        operator.setUserId(13L);
        product.setShop(shop);
        product.setProductId(25L);

        userProductMap.setCreateTime(new Date());
        userProductMap.setShop(shop);
        userProductMap.setPoint(12);
        userProductMap.setUser(user);
        userProductMap.setOperator(operator);
        userProductMap.setProduct(product);

        userProductMapDao.insertUserProductMap(userProductMap);

    }

    @Test
    public void testQueryList() {
        UserProductMap userProductMap = new UserProductMap();
        Shop shop = new Shop();
        PersonInfo user = new PersonInfo();
        PersonInfo operator = new PersonInfo();
        Product product = new Product();

        product.setProductName("可乐");
        user.setName("袁");
       /* operator.setUserId(13L);
        product.setShop(shop);
        product.setProductId(25L);*/

        //userProductMap.setUser(user);
        userProductMap.setProduct(product);

        List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapAllList(userProductMap);
        userProductMaps.forEach(System.out::println);
    }

}
