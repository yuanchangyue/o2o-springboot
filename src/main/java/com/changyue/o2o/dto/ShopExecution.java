package com.changyue.o2o.dto;

import com.changyue.o2o.emums.ShopStateEnum;
import com.changyue.o2o.entity.Shop;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 23:42
 */
public class ShopExecution {

    private int state;
    private String stateInfo;
    private int count;
    private Shop shop;
    private List<Shop> shopList;
    private PageInfo<Shop> shopPageInfo;

    public ShopExecution() {
    }

    /**
     * 返回失败的构造器
     *
     * @param stateEnum
     */
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 返回成功的构造器
     *
     * @param stateEnum    状态
     * @param shopPageInfo 分页
     */
    public ShopExecution(ShopStateEnum stateEnum, PageInfo<Shop> shopPageInfo) {
        this.shopPageInfo = shopPageInfo;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 是成功的构造器
     *
     * @param stateEnum 状态
     * @param shopList  商铺列表
     */
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.shopList = shopList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    /**
     * 是成功的构造器
     *
     * @param stateEnum 状态
     * @param shopList  商铺列表
     */
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList, PageInfo<Shop> shopPageInfo) {
        this.shopList = shopList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.shop = shop;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public PageInfo<Shop> getShopPageInfo() {
        return shopPageInfo;
    }

    public void setShopPageInfo(PageInfo<Shop> shopPageInfo) {
        this.shopPageInfo = shopPageInfo;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }


}
