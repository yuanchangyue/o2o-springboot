package com.changyue.o2o.dao;

import com.changyue.o2o.entity.Award;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 奖品数据库接口
 * @author: 袁阊越
 * @create: 2019-10-15 13:06
 */
@Repository
public interface AwardDao {

    /**
     * 分页查询奖品
     *
     * @param awardCondition 查询奖品的条件
     * @return 分页的奖品list
     */
    List<Award> queryAwardAllList(@Param("awardCondition") Award awardCondition);


    /**
     * 分页查询奖品
     *
     * @param awardCondition 查询奖品的条件
     * @param rowIndex       从第几页开始
     * @param pageSize       一页的数量
     * @return 分页的奖品list
     */
    List<Award> queryAwardList(@Param("awardCondition") Award awardCondition, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);


    /**
     * 查询奖品数量
     *
     * @param awardCondition 查询奖品的条件
     * @return 奖品数量
     */
    int queryAwardCount(@Param("awardCondition") Award awardCondition);

    /**
     * 根据id 查询奖品
     *
     * @param awardId id
     * @return 奖品信息
     */
    Award queryAwardById(long awardId);

    /**
     * 插入奖品
     *
     * @param award 插入的奖品信息
     * @return 影响行数
     */
    int insertAward(Award award);

    /**
     * 更新奖品
     *
     * @param award 更新的奖品信息
     * @return 影响行数
     */
    int updateAward(Award award);

    /**
     * 根据awardId和shopId删除奖品
     *
     * @param awardId 奖品id
     * @param shopId  商铺id
     * @return 影响行数
     */
    int deleteAward(@Param("awardId") long awardId, @Param("shopId") long shopId);


}
