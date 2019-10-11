package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.ShopDao;
import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.dto.ShopExecution;
import com.changyue.o2o.emums.ShopStateEnum;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.exceptions.ShopOperationException;
import com.changyue.o2o.service.ShopService;
import com.changyue.o2o.util.ImageUtil;
import com.changyue.o2o.util.PathUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-17 18:41
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    /**
     * 获得商铺列表
     *
     * @param shopCondition 带条件的商铺信息
     * @param pageIndex     页码
     * @param pageSize      页数
     * @return 商铺执行包装类
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        // 分页操作
        PageHelper.startPage(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopListByAll(shopCondition);
        PageInfo<Shop> shopPageInfo = new PageInfo<>(shopList, 5);

        //存放包装类中
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {

            shopExecution.setShopPageInfo(shopPageInfo);
            shopExecution.setShopList(shopList);

        } else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    /**
     * 通过shopId获得商铺
     *
     * @param shopId shopId
     * @return 商铺
     */
    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryById(shopId);
    }

    /**
     * 修改商铺信息
     *
     * @param shop      商铺
     * @param thumbnail 缩略图
     * @return 商铺异常包装类
     */
    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) {

        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryById(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteImgFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryById(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (IOException e) {
                throw new ShopOperationException("modifyShop error :" + e.getMessage());
            }
        }
    }

    /**
     * 注册商铺
     *
     * @param shop      注册商铺信息
     * @param thumbnail 缩略图
     * @return 商铺操作包装类
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effect = shopDao.insertShop(shop);
            if (effect <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new RuntimeException("addShop error: " + e.getMessage());
                    }
                    effect = shopDao.updateShop(shop);
                    if (effect <= 0) {
                        throw new ShopOperationException("更新图片失败！");
                    }
                }
            }

        } catch (Exception e) {
            throw new ShopOperationException("addShop error: " + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) throws IOException {
        String dest = PathUtils.getShopImagePath(shop.getShopId());
        String imgAddr = ImageUtil.generateThumbnails(thumbnail, dest);
        shop.setShopImg(imgAddr);
    }

}
