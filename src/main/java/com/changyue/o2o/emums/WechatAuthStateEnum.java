package com.changyue.o2o.emums;

/**
 * @program: o2o
 * @description: 微信认证枚举类
 * @author: YuanChangYue
 * @create: 2019-09-24 14:40
 */
public enum WechatAuthStateEnum {

    /**
     * 微信认证状态
     */
    WECHAT_AUTH_SUCCESS(3001, "微信账号创建成功"),
    WECHAT_AUTH_NULL(-3000, "微信认证为空");

    private int state;
    private String stateInfo;

    WechatAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
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

    public static WechatAuthStateEnum stateOf(int index) {
        for (WechatAuthStateEnum sta : values()) {
            if (sta.getState() == index) {
                return sta;
            }
        }
        return null;
    }

}
