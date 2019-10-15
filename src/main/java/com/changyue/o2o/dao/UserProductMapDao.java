package com.changyue.o2o.dao;

import com.changyue.o2o.entity.UserProductMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductMapDao {


    /**
     * 插入用户购买的信息
     *
     * @param userProductMap 用户的购买的信息
     * @return 影响行数
     */
    int insertUserProductMap(UserProductMap userProductMap);


    /**
     * 查询全部用户的购买记录
     *
     * @param userProductMap 用户购买
     * @return 全部用户的购买记录的List
     */
    List<UserProductMap> queryUserProductMapAllList(@Param("userProductCondition") UserProductMap userProductMap);

    /**
     * 查询带条件的分页用户购买的记录
     *
     * @param userProductMap 查询的条件
     * @param rowIndex       从第几页开始
     * @param pageSize       一页的数量
     * @return 用户兑将记录List
     */
    List<UserProductMap> queryUserProductMapList(@Param("userProductCondition") UserProductMap userProductMap, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);


    /**
     * 通过购买记录的id查询购买信息
     *
     * @param userProductId 用户购买记录的id
     * @return 购买的信息
     */
    UserProductMap queryUserProductMapById(Integer userProductId);


}