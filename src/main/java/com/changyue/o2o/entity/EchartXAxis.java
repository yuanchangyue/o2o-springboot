package com.changyue.o2o.entity;

import java.util.HashSet;

/**
 * @program: o2o-springboot
 * @description:
 * @author: 袁阊越
 * @create: 2019-11-06 20:40
 */
public class EchartXAxis {
    private String type = "category";
    private HashSet<String> data;

    public String getType() {
        return type;
    }

    public HashSet<String> getData() {
        return data;
    }

    public void setData(HashSet<String> data) {
        this.data = data;
    }
}
