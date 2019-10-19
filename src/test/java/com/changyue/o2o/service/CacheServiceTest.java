package com.changyue.o2o.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: o2o
 * @description:
 * @author: YuanChangYue
 * @create: 2019-09-24 19:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest  {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void testCache() {
        cacheService.removeFromCache(areaService.AREA_LIST_KEY);
        cacheService.removeFromCache(headLineService.HAED_LINE_KEK);
        cacheService.removeFromCache(shopCategoryService.SHOP_CATEGORY_KEY);
    }
}
