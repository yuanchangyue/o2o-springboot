package com.changyue.o2o.dao;

import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.UserShopMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 用户在商铺产生积分
 * @author: 袁阊越
 * @create: 2019-10-15 23:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserShopMapDaoTest {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Test
    public void testInsertUserShop() {
        UserShopMap userShopMap = new UserShopMap();
        Shop shop = new Shop();
        PersonInfo user = new PersonInfo();
        user.setUserId(8L);
        shop.setShopId(19L);
        userShopMap.setShop(shop);
        userShopMap.setCreateTime(new Date());
        userShopMap.setPoint(16);
        userShopMap.setUser(user);
        userShopMapDao.insertUserShopMap(userShopMap);

    }

    @Test
    public void testQueryUserShopMapList() {
        UserShopMap userShopMap = new UserShopMap();
        Shop shop = new Shop();
        PersonInfo user = new PersonInfo();

        List<UserShopMap> userShopMaps = userShopMapDao.selectUserShopMapAllList(userShopMap);
        userShopMaps.forEach(System.out::println);

    }

    @Test
    public void testQueryUserShopMapListBy() {
        UserShopMap userShopMap = userShopMapDao.selectUserShopMapById(8L, 19L);
        System.out.println(userShopMap);
    }


    @Test
    public void testQueryUpdateUserShpMap() {
        UserShopMap userShopMap = userShopMapDao.selectUserShopMapById(8L, 19L);
        userShopMap.setPoint(100);
        int i = userShopMapDao.updateUserShopMap(userShopMap);
        Assert.assertEquals(1, i);
    }


}
