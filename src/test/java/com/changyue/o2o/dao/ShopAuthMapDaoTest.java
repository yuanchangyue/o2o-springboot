package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.ShopAuthMap;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 商铺授权
 * @author: 袁阊越
 * @create: 2019-10-16 17:31
 */
public class ShopAuthMapDaoTest extends BaseTest {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Test
    public void testInsertMap() {
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        PersonInfo employee = new PersonInfo();
        employee.setUserId(10L);
        shopAuthMap.setEmployee(employee);
        Shop shop = new Shop();
        shop.setShopId(19L);
        shopAuthMap.setCreateTime(new Date());
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMap.setTitle("送货员");
        shopAuthMap.setTitleFlag(1);
        shopAuthMap.setEnableStatus(1);
        shopAuthMap.setShop(shop);
        int i = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
        Assert.assertEquals(1, i);
    }

    @Test
    public void testQueryShopAuth() {
        ShopAuthMap shopAuthMap = shopAuthMapDao.queryShopAuthMapByShopAuthId(3L);
        List<ShopAuthMap> shopAuthMaps = shopAuthMapDao.queryShopAuthMapAllListByShopId(19L);
        List<ShopAuthMap> shopAuthMaps1 = shopAuthMapDao.queryShopAuthMapListByShopId(19L, 0, 2);
        Assert.assertEquals(2, shopAuthMaps1.size());
        System.out.println("shopAuthMap = " + shopAuthMap);
        shopAuthMaps.forEach(System.out::println);
    }

    @Test
    public void testDeleteShopAuthMap() {
        int i = shopAuthMapDao.deleteShopAuthMap(3L);
        Assert.assertEquals(i, 1);
    }

    @Test
    public void testUpdateShopAuthMap() {
        ShopAuthMap shopAuthMap = new ShopAuthMap();
        shopAuthMap.setShopAuthId(3L);
        shopAuthMap.setLastEditTime(new Date());
        shopAuthMap.setTitle("测试员");
        shopAuthMap.setTitleFlag(0);
        shopAuthMap.setEnableStatus(0);
        int i = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
        Assert.assertEquals(1, i);
    }

}
