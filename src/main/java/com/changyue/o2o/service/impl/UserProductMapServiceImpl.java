package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.UserProductMapDao;
import com.changyue.o2o.dto.UserProductMapExecution;
import com.changyue.o2o.entity.UserProductMap;
import com.changyue.o2o.service.UserProductMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 用户消费商品业务实现类
 * @author: 袁阊越
 * @create: 2019-11-02 00:10
 */
@Service
public class UserProductMapServiceImpl implements UserProductMapService {

    @Autowired
    private UserProductMapDao userProductMapDao;

<<<<<<< HEAD
=======

>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize) {

        if (userProductCondition != null && pageIndex != null && pageSize != null) {

            PageHelper.startPage(pageIndex, pageSize);
<<<<<<< HEAD
            List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapAllList(userProductCondition);
            PageInfo<UserProductMap> productMapPageInfo = new PageInfo<>(userProductMaps,5);
=======
            List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapList(userProductCondition, pageIndex, pageSize);
            PageInfo<UserProductMap> productMapPageInfo = new PageInfo<>(userProductMaps);
>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5

            UserProductMapExecution userProductMapExecution = new UserProductMapExecution();
            userProductMapExecution.setUserProductMapPageInfo(productMapPageInfo);
            return userProductMapExecution;
        } else {
            return null;
        }

    }
}
