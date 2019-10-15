package com.changyue.o2o.entity;

import java.util.Date;

/**
 * @Description: 用户已经领取的奖品
 * @Author: YuanChangYue
 * @date: 2019/10/9/009
 */
public class UserAwardMap {

    private Long userAwardId;
    private Date expireTime;
    private Date createTime;
    private Date lastEditTime;

    /**
     * 是否进行了兑换 0：未兑换 1：已兑换
     */
    private Integer usedStatus;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 客户
     */
    private PersonInfo user;
    /**
     * 奖品
     */
    private Award award;
    /**
     * 商铺
     */
    private Shop shop;
    /**
     * 操作员
     */
    private PersonInfo operator;

    public Long getUserAwardId() {
        return userAwardId;
    }

    public void setUserAwardId(Long userAwardId) {
        this.userAwardId = userAwardId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
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

    public Integer getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public PersonInfo getUser() {
        return user;
    }

    public void setUser(PersonInfo user) {
        this.user = user;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public PersonInfo getOperator() {
        return operator;
    }

    public void setOperator(PersonInfo operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "UserAwardMap{" +
                "userAwardId=" + userAwardId +
                ", expireTime=" + expireTime +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", usedStatus=" + usedStatus +
                ", point=" + point +
                ", user=" + user +
                ", award=" + award +
                ", shop=" + shop +
                ", operator=" + operator +
                '}';
    }
}
