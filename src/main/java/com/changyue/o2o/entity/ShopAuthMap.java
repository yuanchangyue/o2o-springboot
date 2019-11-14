package com.changyue.o2o.entity;

import java.util.Date;

/**
 * @program: shopping
 * @description: 店铺授权
 * @author: ChangYue
 * @create: 2019-03-03 22:08
 */
public class ShopAuthMap {

    private Long shopAuthId;
    /**
     * 职称名
     */
    private String title;
    /**
     * 职称符号
     */
    private Integer titleFlag;
    /**
     * 有效状态
     */
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
    /**
     * 员工
     */
    private PersonInfo employee;
    private Shop shop;

    public Long getShopAuthId() {
        return shopAuthId;
    }

    public void setShopAuthId(Long shopAuthId) {
        this.shopAuthId = shopAuthId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTitleFlag() {
        return titleFlag;
    }

    public void setTitleFlag(Integer titleFlag) {
        this.titleFlag = titleFlag;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public PersonInfo getEmployee() {
        return employee;
    }

    public void setEmployee(PersonInfo employee) {
        this.employee = employee;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "ShopAuthMap{" +
                "shopAuthId=" + shopAuthId +
                ", title='" + title + '\'' +
                ", titleFlag=" + titleFlag +
                ", enableStatus=" + enableStatus +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", employee=" + employee +
                ", shop=" + shop +
                '}';
    }
}
