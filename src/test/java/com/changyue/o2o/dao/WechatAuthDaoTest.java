package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.WechatAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @program: o2o
 * @description: 微信认证测试
 * @author: YuanChangYue
 * @create: 2019-09-24 14:09
 */
public class WechatAuthDaoTest extends BaseTest {
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
