package com.changyue.o2o.service;

import com.changyue.o2o.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: o2o
 * @description:
 * @author: YuanChangYue
 * @create: 2019-09-24 19:56
 */
public class CacheServiceTest extends BaseTest {

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
