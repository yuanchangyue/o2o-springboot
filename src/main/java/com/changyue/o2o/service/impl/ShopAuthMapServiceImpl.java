package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.ShopAuthMapDao;
import com.changyue.o2o.dto.ShopAuthMapExecution;
import com.changyue.o2o.emums.ShopAuthMapEnum;
import com.changyue.o2o.entity.ShopAuthMap;
import com.changyue.o2o.exceptions.ShopAuthMapOperationException;
import com.changyue.o2o.service.ShopAuthMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description: 商铺授权业务实现类
 * @author: 袁阊越
 * @create: 2019-10-16 18:28
 */
@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        if (shopId != null && pageIndex != null && pageSize != null) {

            PageHelper.startPage(pageIndex, pageSize);
            List<ShopAuthMap> shopAuthMaps = shopAuthMapDao.queryShopAuthMapAllListByShopId(shopId);
            PageInfo<ShopAuthMap> shopAuthMapPageInfo = new PageInfo<>(shopAuthMaps);

            ShopAuthMapExecution shopAuthMapExecution = new ShopAuthMapExecution();
            shopAuthMapExecution.setShopAuthMapPageInfo(shopAuthMapPageInfo);
            return shopAuthMapExecution;
        } else {
            return null;
        }
    }

    @Override
    public ShopAuthMap queryShopAuthMapByShopAuthId(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapByShopAuthId(shopAuthId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) {

        if (shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null
                && shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId() != null) {

            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);


            try {
                //添加授权的信息
                int effect = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                if (effect <= 0) {
                    throw new ShopAuthMapOperationException("添加授权失败！");
                }
                return new ShopAuthMapExecution(ShopAuthMapEnum.SUCCESS, shopAuthMap);
            } catch (Exception e) {
                throw new ShopAuthMapOperationException("添加授权失败" + e.getMessage());
            }
        } else {
            return new ShopAuthMapExecution(ShopAuthMapEnum.NULL_SHOP_AUTH_INFO_NULL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) {

        if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null) {
            return new ShopAuthMapExecution(ShopAuthMapEnum.NULL_SHOP_AUTH_ID);
        } else {
            try {
                int effect = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
                if (effect <= 0) {
                    return new ShopAuthMapExecution(ShopAuthMapEnum.INNER_ERROR);
                } else {
                    return new ShopAuthMapExecution(ShopAuthMapEnum.SUCCESS, shopAuthMap);
                }
            } catch (Exception e) {
                throw new ShopAuthMapOperationException("modifyShopAuthMap" + e.getMessage());
            }
        }
    }

}
