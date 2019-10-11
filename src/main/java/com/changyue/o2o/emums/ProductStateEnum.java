package com.changyue.o2o.emums;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-10 16:57
 */
public enum ProductStateEnum {
    /**
     * 商品状态
     */
    SUCCESS(1000, "创建成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY(-1002, "商品为空");

    private int state;
    private String stateInfo;

    ProductStateEnum(int state, String stateInfo) {
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

    public static ProductStateEnum stateOf(int index) {
        for (ProductStateEnum sta : values()) {
            if (sta.getState() == index) {
                return sta;
            }
        }
        return null;
    }
}
