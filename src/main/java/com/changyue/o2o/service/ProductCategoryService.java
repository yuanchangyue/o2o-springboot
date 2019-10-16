package com.changyue.o2o.service;

import com.changyue.o2o.dto.ProductCategoryExecution;
import com.changyue.o2o.entity.ProductCategory;
import com.changyue.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-25 22:37
 */
public interface ProductCategoryService {

    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategories) throws ProductCategoryOperationException;

    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;

}
