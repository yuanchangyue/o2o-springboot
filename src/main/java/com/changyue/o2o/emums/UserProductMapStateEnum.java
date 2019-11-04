package com.changyue.o2o.emums;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-08 22:14
 */
public enum UserProductMapStateEnum {

    /**
     * 用户类别状态
     */
    SUCCESS(1, "创建成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY_LIST(-1002, "添加少于1");

    private int state;
    private String stateInfo;

    UserProductMapStateEnum(int state, String stateInfo) {
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

    public static UserProductMapStateEnum stateOf(int index) {
        for (UserProductMapStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
