package com.changyue.o2o.service;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-12 21:26
 */
public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Autowired
    private CacheService cacheService;

    @Test
    public void getAreaList() {
        List<Area> areaList = areaService.getAreaList();
        Assert.assertEquals("和园一号", areaList.get(0).getAreaName());
        cacheService.removeFromCache(areaService.AREA_LIST_KEY);
    }
}
