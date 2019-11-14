package com.changyue.o2o.dto;

import com.changyue.o2o.emums.ShopAuthMapEnum;
import com.changyue.o2o.emums.ShopStateEnum;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.ShopAuthMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 23:42
 */
public class ShopAuthMapExecution {

    private int state;
    private String stateInfo;
    private int count;
    private ShopAuthMap shopAuthMap;
    private List<ShopAuthMap> shopAuthMapList;
    private PageInfo<ShopAuthMap> shopAuthMapPageInfo;

    public ShopAuthMapExecution() {
    }

    /**
     * 返回失败的构造器
     *
     * @param stateEnum
     */
    public ShopAuthMapExecution(ShopAuthMapEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 返回成功的构造器
     *
     * @param stateEnum           状态
     * @param shopAuthMapPageInfo 分页
     */
    public ShopAuthMapExecution(ShopAuthMapEnum stateEnum, PageInfo<ShopAuthMap> shopAuthMapPageInfo) {
        this.shopAuthMapPageInfo = shopAuthMapPageInfo;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 是成功的构造器
     *
     * @param stateEnum       状态
     * @param shopAuthMapList 商铺列表
     */
    public ShopAuthMapExecution(ShopAuthMapEnum stateEnum, List<ShopAuthMap> shopAuthMapList) {
        this.shopAuthMapList = shopAuthMapList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 是成功的构造器
     *
     * @param stateEnum       状态
     * @param shopAuthMapList 商铺列表
     */
    public ShopAuthMapExecution(ShopAuthMapEnum stateEnum, List<ShopAuthMap> shopAuthMapList, PageInfo<ShopAuthMap> shopAuthMapPageInfo) {
        this.shopAuthMapList = shopAuthMapList;
        this.state = stateEnum.getState();
        this.shopAuthMapPageInfo = shopAuthMapPageInfo;
        this.stateInfo = stateEnum.getStateInfo();
    }

    public ShopAuthMapExecution(ShopAuthMapEnum stateEnum, ShopAuthMap shopAuthMap) {
        this.shopAuthMap = shopAuthMap;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ShopAuthMap getShopAuthMap() {
        return shopAuthMap;
    }

    public void setShopAuthMap(ShopAuthMap shopAuthMap) {
        this.shopAuthMap = shopAuthMap;
    }

    public List<ShopAuthMap> getShopAuthMapList() {
        return shopAuthMapList;
    }

    public void setShopAuthMapList(List<ShopAuthMap> shopAuthMapList) {
        this.shopAuthMapList = shopAuthMapList;
    }

    public PageInfo<ShopAuthMap> getShopAuthMapPageInfo() {
        return shopAuthMapPageInfo;
    }

    public void setShopAuthMapPageInfo(PageInfo<ShopAuthMap> shopAuthMapPageInfo) {
        this.shopAuthMapPageInfo = shopAuthMapPageInfo;
    }
}
