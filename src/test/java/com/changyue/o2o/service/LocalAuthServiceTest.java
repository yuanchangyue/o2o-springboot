package com.changyue.o2o.service;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.dto.LocalAuthExecution;
import com.changyue.o2o.emums.LocalAuthStateEnum;
import com.changyue.o2o.entity.LocalAuth;
import com.changyue.o2o.entity.PersonInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @program: o2o
 * @description: 平台账号业务测试类
 * @author: YuanChangYue
 * @create: 2019-09-25 16:35
 */
public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    private LocalAuthService localAuthService;

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

        LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
        Assert.assertEquals(localAuthExecution.getState(), LocalAuthStateEnum.LOCAL_AUTH_CREATE_SUCCESS.getState());

    }

    @Test
    public void testCQueryLocalAuth() {
        LocalAuth localAuth = localAuthService.getLocalByUserId(14L);
        System.out.println("localAuth = " + localAuth);
        Assert.assertEquals("袁XX", localAuth.getUsername());
    }

    @Test
    public void testBQueryLocalAuthByUserAndPw() {
        LocalAuth localAuth = localAuthService.getLocalByUserNameAndPw("袁XX", "yuanwu");
        System.out.println(localAuth.getPersonInfo().getUserId());
    }

    @Test
    public void testDUpdateLocalAuth() {
        LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(14L, "袁XX", "yuanchangyue", "yuanwu");
        Assert.assertEquals(localAuthExecution.getState(), LocalAuthStateEnum.LOCAL_AUTH_UPDATE_PASSWORD_SUCCESS.getState());
    }

}
