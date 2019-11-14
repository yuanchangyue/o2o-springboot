package com.changyue.o2o.emums;

/**
 * @Author: YuanChangYue
 * @date: 2019/9/25/025
 */
public enum LocalAuthStateEnum {
    /**
     * 平台账号状态
     */
    LOCAL_AUTH_CREATE_SUCCESS(-4000, "微信账号绑定成功"),
    LOCAL_AUTH_NULL(-4002, "平台信息为空"),
    LOCAL_AUTH_UPDATE_PASSWORD_SUCCESS(-4004, "密码更新成功"),
    LOCAL_AUTH_UPDATE_INFO_NULL(-4003, "平台账号修改信息不完整"),
    LOCAL_AUTH_REPEAT(-4001, "该账号已经存在");

    private int state;
    private String stateInfo;

    LocalAuthStateEnum(int state, String stateInfo) {
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

    public static LocalAuthStateEnum stateOf(int index) {
        for (LocalAuthStateEnum sta : values()) {
            if (sta.getState() == index) {
                return sta;
            }
        }
        return null;
    }
}
