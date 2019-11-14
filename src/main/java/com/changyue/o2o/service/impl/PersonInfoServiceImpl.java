package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.PersonInfoDao;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: o2o
 * @description: 用户业务实现类
 * @author: YuanChangYue
 * @create: 2019-09-24 15:37
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo getPersonInfoById(Long userId) {
        return personInfoDao.queryPersonInfo(userId);
    }

}
