package com.changyue.o2o.service;

import com.changyue.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService {

    public static final String HAED_LINE_KEK = "headLineKey";

    /**
     * 获得HeadLine的list并存放在redis中
     *
     * @param headLine 携带条件headline
     * @return headlines 满足条件的headline的list
     */
    List<HeadLine> getHeadLineList(HeadLine headLine);

}
