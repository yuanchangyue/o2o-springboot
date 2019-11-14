package com.changyue.o2o.dto;

import com.changyue.o2o.emums.WechatAuthStateEnum;
import com.changyue.o2o.entity.WechatAuth;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: o2o
 * @description: 微信认证异常类
 * @author: YuanChangYue
 * @create: 2019-09-24 14:31
 */
public class WechatAuthExecution {
    private int state;
    private String stateInfo;
    private int count;
    private WechatAuth wechatAuth;
    private List<WechatAuth> wechatAuths;
    private PageInfo<WechatAuth> wechatAuthPageInfo;

    public WechatAuthExecution() {
    }

    public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum) {
        this.state = wechatAuthStateEnum.getState();
        this.stateInfo = wechatAuthStateEnum.getStateInfo();
    }

    public WechatAuthExecution(WechatAuthStateEnum stateEnum, WechatAuth wechatAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.wechatAuth = wechatAuth;
    }

    public WechatAuthExecution(WechatAuthStateEnum stateEnum, List<WechatAuth> wechatAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.wechatAuths = wechatAuthList;
    }

    public WechatAuthExecution(WechatAuthStateEnum stateEnum, PageInfo<WechatAuth> wechatAuthPageInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.wechatAuthPageInfo = wechatAuthPageInfo;
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

    public WechatAuth getWechatAuth() {
        return wechatAuth;
    }

    public void setWechatAuth(WechatAuth wechatAuth) {
        this.wechatAuth = wechatAuth;
    }

    public List<WechatAuth> getWechatAuths() {
        return wechatAuths;
    }

    public void setWechatAuths(List<WechatAuth> wechatAuths) {
        this.wechatAuths = wechatAuths;
    }

    public PageInfo<WechatAuth> getWechatAuthPageInfo() {
        return wechatAuthPageInfo;
    }

    public void setWechatAuthPageInfo(PageInfo<WechatAuth> wechatAuthPageInfo) {
        this.wechatAuthPageInfo = wechatAuthPageInfo;
    }
}
