package com.changyue.o2o.service;

import com.changyue.o2o.dto.WechatAuthExecution;
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
 * @description:
 * @author: YuanChangYue
 * @create: 2019-09-24 15:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest {
    @Autowired
    private WechatAuthService wechatAuthService;

    @Test
    public void testRegisterWecha() {

        WechatAuth wechatAuth = new WechatAuth();
        wechatAuth.setOpenId("22222");
        wechatAuth.setCreateTime(new Date());

        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("袁阊越");
        personInfo.setEmail("604028466@qq.com");
        personInfo.setGender("男");
        personInfo.setUserType(1);
        wechatAuth.setPersonInfo(personInfo);

        WechatAuthExecution wechatAuthExecution = wechatAuthService.registerWechat(wechatAuth);
        System.out.println(wechatAuthExecution);
    }
}
