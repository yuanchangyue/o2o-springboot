package com.changyue.o2o.service;

import com.changyue.o2o.entity.Area;

import java.util.List;


public interface AreaService {


    public static final String AREA_LIST_KEY = "areaListKey";

    /**
     * 获取区域信息
     * 使用Redis缓存存放区域信息
     *
     * @return 区域信息集合
     */
    List<Area> getAreaList();

}
