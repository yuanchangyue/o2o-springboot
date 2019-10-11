package com.changyue.o2o.entity;

import java.util.Date;

/**
 * @program: o2o
 * @description: 商铺商品一天的销量
 * @author: YuanChangYue
 * @create: 2019-10-09 15:57
 */
public class ProductSellDaily {
    /**
     * 某天的销量 精确到天
     */
    private Date createTime;
    /**
     * 总销量
     */
    private Integer total;
    /**
     * 具体的商品
     */
    private Product product;
    /**
     * 对应的商铺
     */
    private Shop shop;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
