package com.changyue.o2o.entity;

import java.util.List;

/**
 * @program: o2o-springboot
 * @description:
 * @author: 袁阊越
 * @create: 2019-11-04 23:07
 */
public class EchartSeries {
    private String name ;
    private String type = "bar";
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }


}
