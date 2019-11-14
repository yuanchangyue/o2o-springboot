package com.changyue.o2o.service;

import com.changyue.o2o.entity.PersonInfo;

/**
 * @program: o2o
 * @description:
 * @author: YuanChangYue
 * @create: 2019-09-24 15:35
 */
public interface PersonInfoService {

    /**
     * 通过userId查找用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    PersonInfo getPersonInfoById(Long userId);
}
