package com.changyue.o2o.dto;

import com.changyue.o2o.emums.ProductStateEnum;
import com.changyue.o2o.entity.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-10 16:52
 */
public class ProductExecution {

    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //商品数量
    private int count;

    private Product product;

    private List<Product> productList;

    private PageInfo<Product> productPageInfo;

    public ProductExecution() {

    }

    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

    public ProductExecution(ProductStateEnum stateEnum, PageInfo<Product> productPageInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productPageInfo = productPageInfo;
    }

    public PageInfo<Product> getProductPageInfo() {
        return productPageInfo;
    }

    public void setProductPageInfo(PageInfo<Product> productPageInfo) {
        this.productPageInfo = productPageInfo;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ProductExecution{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", count=" + count +
                ", product=" + product +
                ", productList=" + productList +
                '}';
    }
}
