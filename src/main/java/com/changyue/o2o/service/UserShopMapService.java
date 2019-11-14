package com.changyue.o2o.service;


import com.changyue.o2o.dto.UserShopMapExecution;
import com.changyue.o2o.entity.UserShopMap;

public interface UserShopMapService {

    /**
     * 根据传入的查询信息分页查询用户的积分列表
     *
     * @param userShopMapCondition 条件
     * @param pageIndex            开始
     * @param pageSize             一页的容量
     * @return UserShopMapExecution
     */
    UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,
                                         int pageIndex, int pageSize);

    /**
     * 根据用户的id和商铺id查询该用户在该商铺下的积分情况
     *
     * @param userId 用户id
     * @param shopId 商铺id
     * @return userShopMap
     */
    UserShopMap getUserShopMap(long userId, long shopId);

}
