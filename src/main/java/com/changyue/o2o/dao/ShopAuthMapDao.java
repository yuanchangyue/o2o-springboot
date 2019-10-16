package com.changyue.o2o.dao;

import com.changyue.o2o.entity.ShopAuthMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 袁阊越
 * @Title: ShopAuthMapMapper
 * @Package com.changyue.o2o.dao
 * @Description: 商铺授权
 * @date 2019/10/16/016
 */
@Repository
public interface ShopAuthMapDao {


    /**
     * 查询该shopId的全部商铺授权
     *
     * @param shopId   商铺id
     * @param rowIndex 从第几页开始
     * @param pageSize 一页的数量
     * @return 商铺授权List
     */
    List<ShopAuthMap> queryShopAuthMapListByShopId(@Param("shopId") long shopId, @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询该shopId的全部商铺授权
     *
     * @param shopId 商铺id
     * @return 商铺授权List
     */
    List<ShopAuthMap> queryShopAuthMapAllListByShopId(@Param("shopId") long shopId);

    /**
     * 根据商铺id授权的总数
     *
     * @param shopId 商铺id
     * @return 授权的总数
     */
    int queryShopAuthMapCount(long shopId);

    /**
     * 移除员工的授权
     *
     * @param shopAuthId 关系id
     * @return 影响行数
     */
    int deleteShopAuthMap(long shopAuthId);

    /**
     * 插入商铺的授权信息
     *
     * @param shopAuthMap 商铺授权信息
     * @return 影响行数
     */
    int insertShopAuthMap(ShopAuthMap shopAuthMap);

    /**
     * 通过授权id 查询员工的授权信息
     *
     * @param shopAuthId 授权id
     * @return 员工的信息
     */
    ShopAuthMap queryShopAuthMapByShopAuthId(long shopAuthId);

    /**
     * 更新商铺授权信息
     *
     * @param shopAuthMap 商铺授权信息
     * @return 影响行数
     */
    int updateShopAuthMap(ShopAuthMap shopAuthMap);


}