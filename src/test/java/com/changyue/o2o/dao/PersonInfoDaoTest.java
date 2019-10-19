package com.changyue.o2o.dao;

import com.changyue.o2o.entity.PersonInfo;
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
 * @create: 2019-09-24 13:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testQueryPersonInfo() {
        PersonInfo personInfo = personInfoDao.queryPersonInfo(8);
        System.out.println(personInfo);
    }

    @Test
    public void testInsertPersonInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("测试！");
        personInfo.setCreateTime(new Date());
        personInfo.setEmail("XXXXXXX@qq.com");
        personInfo.setEnableStatus(1);
        personInfo.setUserType(1);
        personInfo.setLastEditTime(new Date());
        personInfo.setProfileImg("///");
        personInfoDao.insertPersonInf(personInfo);
    }
}
