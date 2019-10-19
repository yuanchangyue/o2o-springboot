package com.changyue.o2o.dao;

import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.WechatAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @program: o2o
 * @description: 微信认证测试
 * @author: YuanChangYue
 * @create: 2019-09-24 14:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthDaoTest {
    @Autowired
    WechatAuthDao wechatAuthDao;

    @Test
    public void testQuery() {
        WechatAuth wechatAuth = wechatAuthDao.queryWechatInfoByOpenId("123");
        System.out.println("wechatAuth = " + wechatAuth);
    }

    @Test
    public void testInsert() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(8L);

        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setCreateTime(new Date());
        wechatAuth.setOpenId("23");
        wechatAuth.setPersonInfo(personInfo);

        wechatAuthDao.insertWechatAuth(wechatAuth);
    }
}
