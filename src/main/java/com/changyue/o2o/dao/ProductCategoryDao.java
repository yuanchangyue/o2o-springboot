package com.changyue.o2o.dao;

import com.changyue.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-25 22:27
 */
public interface ProductCategoryDao {


    /**
     * 查询商品类别
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);

}
