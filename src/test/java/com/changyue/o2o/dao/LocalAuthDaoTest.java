package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.LocalAuth;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.util.MD5;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @program: o2o
 * @description:
 * @author: YuanChangYue
 * @create: 2019-09-24 21:38
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void testAInsertLocalAuth() {

        LocalAuth localAuth = new LocalAuth();
        localAuth.setUsername("袁XX");
        localAuth.setPassword("yuanchangyue");
        localAuth.setLastEditTime(new Date());
        localAuth.setCreateTime(new Date());

        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(14L);

        localAuth.setPersonInfo(personInfo);

        localAuthDao.insertLocalAuth(localAuth);

    }

    @Test
    public void testCQueryLocalAuth() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(14);
        System.out.println("localAuth = " + localAuth);
        Assert.assertEquals("袁XX", localAuth.getUsername());
    }

    @Test
    public void testBQueryLocalAuthByUserAndPw() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPw("袁XX", MD5.getMd5("yuanwu"));
        System.out.println(localAuth.getPersonInfo().getUserId());
    }

    @Test
    public void testDUpdateLocalAuth() {
        int effect = localAuthDao.updateLocalAuth(14L, "袁XX", "yuanchangyue", "yuanwu", new Date());
        Assert.assertEquals(1, effect);
    }
}
