package com.changyue.o2o.dao;

import com.changyue.o2o.entity.*;
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
 * @description: 用户兑奖测试
 * @author: 袁阊越
 * @create: 2019-10-15 16:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAwardMapDaoTest {

    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Test
    public void testInsertUserAwardMap() {
        UserAwardMap userAwardMap = new UserAwardMap();
        Award award = new Award();
        Shop shop = new Shop();
        PersonInfo user = new PersonInfo();
        PersonInfo o = new PersonInfo();
        user.setUserId(13L);
        o.setUserId(13L);
        shop.setShopId(19L);
        award.setAwardId(3L);
        userAwardMap.setAward(award);
        userAwardMap.setUsedStatus(1);
        userAwardMap.setCreateTime(new Date());
        userAwardMap.setShop(shop);
        userAwardMap.setPoint(12);
        userAwardMap.setOperator(o);
        userAwardMap.setUser(user);
        int i = userAwardMapDao.insertUserAwardMap(userAwardMap);
        Assert.assertEquals(i, 1);
    }

    @Test
    public void testQueryUserAwardMapList() {
        UserAwardMap userAwardMap = new UserAwardMap();

        List<UserAwardMap> list = userAwardMapDao.queryUserAwardMapALLList(userAwardMap);
        List<UserAwardMap> list1 = userAwardMapDao.queryUserAwardMapList(userAwardMap, 0, 4);

        list.forEach(System.out::println);
        System.out.println("#############################");
        list1.forEach(System.out::println);
        Assert.assertEquals(list.size(), 4);
        Assert.assertEquals(list1.size(), 4);

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("袁");
        PersonInfo personInfo1 = new PersonInfo();
        personInfo1.setName("测试");
        userAwardMap.setUser(personInfo);
        userAwardMap.setOperator(personInfo1);
        List<UserAwardMap> userAwardMapDaos = userAwardMapDao.queryUserAwardMapALLList(userAwardMap);
        System.out.println("#############################");
        userAwardMapDaos.forEach(System.out::println);

    }


    @Test
    public void testQueryUserAwardMapById() {
        UserAwardMap userAwardMap = userAwardMapDao.queryUserAwardMapById(1L);
        System.out.println("userAwardMap = " + userAwardMap);
    }


    @Test
    public void testUpdateUserAwardMap() {
        UserAwardMap userAwardMap = new UserAwardMap();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(13L);
        userAwardMap.setUser(personInfo);
        userAwardMap.setUserAwardId(2L);
        userAwardMap.setUsedStatus(0);
        userAwardMap.setLastEditTime(new Date());
        int i = userAwardMapDao.updateUserAwardMap(userAwardMap);
        Assert.assertEquals(i, 1);
    }

}
