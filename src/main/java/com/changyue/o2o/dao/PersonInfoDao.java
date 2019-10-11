package com.changyue.o2o.dao;

import com.changyue.o2o.entity.PersonInfo;

/**
 * @program: o2o
 * @description: 用户信息
 * @author: YuanChangYue
 * @create: 2019-09-24 12:51
 */
public interface PersonInfoDao {

    /**
     * 查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    PersonInfo queryPersonInfo(long userId);

    /**
     * 添加用户信息
     * @param personInfo 用户信息
     * @return 影响
     */
    int insertPersonInf(PersonInfo personInfo);


}
