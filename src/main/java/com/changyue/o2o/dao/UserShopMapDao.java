package com.changyue.o2o.dao;

import com.changyue.o2o.entity.UserShopMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserShopMapDao {


    /**
     * 用户在商铺增加积分记录
     *
     * @param userShopMap 积分信息
     * @return 影响行数
     */
    int insertUserShopMap(UserShopMap userShopMap);


    /**
     * 用户在该商铺的积分信息 用户id在商铺id下是否有积分
     *
     * @param userId 用户id
     * @param shopId 商铺id
     * @return 用户在该商铺的积分信息
     */
    UserShopMap selectUserShopMapById(@Param("userId") long userId, @Param("shopId") long shopId);


    /**
     * 根据条件查询全部的积分信息
     *
     * @param userShopMap 查询的条件
     * @param rowIndex    从第几页开始
     * @param pageSize    一页的数量
     * @return 全部的积分信息
     */
    List<UserShopMap> selectUserShopMapList(@Param("userShopCondition") UserShopMap userShopMap, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);


    /**
     * 根据条件查询全部的积分信息
     *
     * @param userShopMap 查询的条件
     * @return 全部的积分信息
     */
    List<UserShopMap> selectUserShopMapAllList(@Param("userShopCondition") UserShopMap userShopMap);


    /**
     * 更新用户在商铺下的积分信息
     *
     * @param userShopMap 需要修改的积分信息
     * @return 影响行数
     */
    int updateUserShopMap(UserShopMap userShopMap);


}