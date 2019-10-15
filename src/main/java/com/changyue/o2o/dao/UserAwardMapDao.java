package com.changyue.o2o.dao;

import com.changyue.o2o.entity.UserAwardMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 用户兑奖
 * @author: 袁阊越
 * @create: 2019-10-15 15:14
 */
@Repository
public interface UserAwardMapDao {

    /**
     * 直接查询出全部的兑换记录
     *
     * @param userAwardMap 查询条件
     * @return 全部的兑奖记录List
     */
    List<UserAwardMap> queryUserAwardMapALLList(@Param("userAwardCondition") UserAwardMap userAwardMap);


    /**
     * 查询带条件的分页用户兑将记录
     *
     * @param userAwardMap 查询的条件
     * @param rowIndex     从第几页开始
     * @param pageSize     一页的数量
     * @return 用户兑将记录List
     */
    List<UserAwardMap> queryUserAwardMapList(@Param("userAwardCondition") UserAwardMap userAwardMap, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);


    /**
     * 通过兑奖的id查询兑奖信息
     *
     * @param userAwardId 兑奖的id
     * @return 兑奖的信息
     */
    UserAwardMap queryUserAwardMapById(long userAwardId);

    /**
     * 用户兑奖的记录总记录数
     *
     * @param userAwardMap 查询的条件
     * @return 影响行数
     */
    int queryUserAwardCount(@Param("userAwardCondition") UserAwardMap userAwardMap);


    /**
     * 插入用户兑奖信息
     *
     * @param userAwardMap 兑奖信息
     * @return 影响行数
     */
    int insertUserAwardMap(UserAwardMap userAwardMap);

    /**
     * 更新奖品的兑换状态
     *
     * @param userAwardMap 用户的兑奖信息
     * @return 影响行数
     */
    int updateUserAwardMap(UserAwardMap userAwardMap);


}
