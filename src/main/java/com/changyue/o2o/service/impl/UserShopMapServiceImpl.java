package com.changyue.o2o.service.impl;

/**
 * @program: o2o-springboot-master
 * @description: 用户和商品之间的实现类
 * @author: ChangYue
 * @create: 2019-11-09 23:43
 */

import com.changyue.o2o.dao.UserShopMapDao;
import com.changyue.o2o.dto.UserShopMapExecution;
import com.changyue.o2o.entity.UserShopMap;
import com.changyue.o2o.service.UserShopMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShopMapServiceImpl implements UserShopMapService {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserShopMapExecution listUserShopMap(
            UserShopMap userShopMapCondition, int pageIndex, int pageSize) {
        if (userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {

            PageHelper.startPage(pageIndex, pageSize);
            List<UserShopMap> userShopMapList = userShopMapDao
                    .selectUserShopMapAllList(userShopMapCondition);
            PageInfo<UserShopMap> userShopMapPageInfo = new PageInfo<>(userShopMapList, 5);

            UserShopMapExecution ue = new UserShopMapExecution();
            ue.setUserShopMapPageInfo(userShopMapPageInfo);

            return ue;
        } else {
            return null;
        }

    }

    @Override
    public UserShopMap getUserShopMap(long userId, long shopId) {
        return userShopMapDao.selectUserShopMapById(userId, shopId);
    }
}
