package com.changyue.o2o.emums;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 23:46
 */
public enum ShopAuthMapEnum {
    /**
     * 商铺状态
     */
    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOP_AUTH_ID(-1002, "ShopAuthId为空"),
    NULL_SHOP_AUTH_INFO_NULL(-1003, "传入的空的信息");

    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    ShopAuthMapEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 根据state返回对应的
     *
     * @param status
     * @return
     */
    public static ShopAuthMapEnum stateOf(int status) {
        for (ShopAuthMapEnum shopStateEnum : values()) {
            if (shopStateEnum.getState() == status) {
                return shopStateEnum;
            }
        }
        return null;
    }

}
