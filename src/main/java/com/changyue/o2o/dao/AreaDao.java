package com.changyue.o2o.dao;

import com.changyue.o2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao {
    /**
     * 返回区域列表
     *
     * @return
     */
    List<Area> queryAll();
}
