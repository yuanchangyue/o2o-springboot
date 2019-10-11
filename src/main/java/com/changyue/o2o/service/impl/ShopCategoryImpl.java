package com.changyue.o2o.service.impl;

import com.changyue.o2o.cache.JedisUtil;
import com.changyue.o2o.dao.ShopCategoryDao;
import com.changyue.o2o.entity.ShopCategory;
import com.changyue.o2o.exceptions.ShopCategoryOpereationException;
import com.changyue.o2o.service.ShopCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-18 20:50
 */
@Service
public class ShopCategoryImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Autowired
    private JedisUtil.Keys jedisKey;


    private static Logger logger = LoggerFactory.getLogger(ShopCategoryImpl.class);

    /**
     * 获得商铺类别并存放在redis中
     *
     * @param shopCategoryCondition 携带条件的shopCategory 进行查询
     * @return shopCategory 查询商铺类别的集合
     */
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        String key = SHOP_CATEGORY_KEY;
        ObjectMapper mapper = new ObjectMapper();
        List<ShopCategory> shopCategories = null;

        //查询默认为空,显示首页大类,parentId为空的店铺类别
        if (shopCategoryCondition == null) {
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            //parentId不为空,则是显示parentId下面的所有大类
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            //列出所有的二级类别
            key = key + "_allsecondlevel";
        }

        /*存放redis*/
        if (!jedisKey.exists(key)) {
            shopCategories = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(shopCategories);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOpereationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                shopCategories = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOpereationException(e.getMessage());
            }
        }
        return shopCategories;
    }


/*    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }*/
}
