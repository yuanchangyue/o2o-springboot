package com.changyue.o2o.dao;

import com.changyue.o2o.entity.ProductImg;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-10 15:31
 */
public interface ProductImgDao {

    /**
     * 查询产品的图片集合
     *
     * @param productId 产品ID
     */
    List<ProductImg> queryProductImgList(Long productId);

    /**
     * 批量添加图片
     *
     * @param productImgList 产品图片集合
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 通过产品ID删除图片
     *
     * @param productId 产品ID
     */
    int deleteProductImgByProductId(long productId);

}
