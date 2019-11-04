package com.changyue.o2o.dto;

import com.changyue.o2o.emums.ShopAuthMapEnum;
import com.changyue.o2o.emums.UserProductMapStateEnum;
import com.changyue.o2o.entity.ShopAuthMap;
import com.changyue.o2o.entity.UserProductMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-08 22:13
 */
public class UserProductMapExecution {

    private int state;
    private String stateInfo;

    private List<UserProductMap> userProductMapList;
    private PageInfo<UserProductMap> userProductMapPageInfo;

    public UserProductMapExecution() {
    }


    /**
     * 操作失败的时候使用的构造器
     */
    public UserProductMapExecution(UserProductMapStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public UserProductMapExecution(UserProductMapStateEnum stateEnum, PageInfo<UserProductMap> userProductMapPageInfo) {
        this.userProductMapPageInfo = userProductMapPageInfo;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    /**
     * 操作成功的时候使用的构造器
     */
    public UserProductMapExecution(UserProductMapStateEnum stateEnum, List<UserProductMap> userProductMapList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userProductMapList = userProductMapList;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<UserProductMap> getUserProductMapList() {
        return userProductMapList;
    }

    public void setUserProductMapList(List<UserProductMap> userProductMapList) {
        this.userProductMapList = userProductMapList;
    }

    public PageInfo<UserProductMap> getUserProductMapPageInfo() {
        return userProductMapPageInfo;
    }

    public void setUserProductMapPageInfo(PageInfo<UserProductMap> userProductMapPageInfo) {
        this.userProductMapPageInfo = userProductMapPageInfo;
    }
}
