package com.changyue.o2o.emums;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-14 23:46
 */
public enum ShopStateEnum {
    /**
     * 商铺状态
     */
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOP_ID(-1002, "商铺ID为空"),
    NULL_SHOP(-1003, "商铺信息为空");

    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 根据state返回对应的
     *
     * @param status
     * @return
     */
    public static ShopStateEnum stateOf(int status) {
        for (ShopStateEnum shopStateEnum : values()) {
            if (shopStateEnum.getState() == status) {
                return shopStateEnum;
            }
        }
        return null;
    }


}
