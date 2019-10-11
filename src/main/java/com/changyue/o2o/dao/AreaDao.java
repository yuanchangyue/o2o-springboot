package com.changyue.o2o.dao;

import com.changyue.o2o.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 返回区域列表
     * @return
     */
    List<Area> queryAll();
}
