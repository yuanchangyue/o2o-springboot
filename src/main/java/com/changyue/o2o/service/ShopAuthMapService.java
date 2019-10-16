package com.changyue.o2o.service;

import com.changyue.o2o.dto.ShopAuthMapExecution;
import com.changyue.o2o.entity.ShopAuthMap;

/**
 * @program: o2o-springboot
 * @description: 商铺授权业务层接口
 * @author: 袁阊越
 * @create: 2019-10-16 18:22
 */

public interface ShopAuthMapService {

    /**
     * 分页查询商铺的授权信息
     *
     * @param shopId    商铺id
     * @param pageIndex 从那一页开始
     * @param pageSize  一页中的数量
     * @return 商铺授权执行包装类
     */
    ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize);


    /**
     * 通过授权id 查询员工的授权信息
     *
     * @param shopAuthId 授权id
     * @return 商铺授权执行包装类
     */
    ShopAuthMap queryShopAuthMapByShopAuthId(Long shopAuthId);


    /**
     * 更新商铺授权信息
     *
     * @param shopAuthMap 商铺授权信息
     * @return 商铺授权执行包装类
     */
    ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap);


    /**
     * 添加商铺的授权信息
     *
     * @param shopAuthMap 商铺授权信息
     * @return 商铺授权执行包装类
     */
    ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap);

}
