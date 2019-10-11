package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.ProductCategoryDao;
import com.changyue.o2o.dao.ProductDao;
import com.changyue.o2o.dto.ProductCategoryExecution;
import com.changyue.o2o.emums.ProductCategoryStateEnum;
import com.changyue.o2o.entity.ProductCategory;
import com.changyue.o2o.exceptions.ProductCategoryOperationException;
import com.changyue.o2o.exceptions.ProductOperationException;
import com.changyue.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-25 22:38
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;



    /**
     * 获得商品类别集合
     *
     * @param shopId 商铺id
     * @return 商品类别集合
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    /**
     * 批量添加商品类别
     *
     * @param productCategories 商品类别集合
     * @return 商品类别异常包装类
     * @throws ProductCategoryOperationException 商品类别操作异常包装类
     */
    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategories) throws ProductCategoryOperationException {
        if (productCategories != null && productCategories.size() > 0) {

            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategories);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("商品类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
            }

        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    /**
     * 商品类别删除
     *
     * @param productCategoryId 商品类别id
     * @param shopId            商铺id
     * @return 商品类别异常包装类
     * @throws ProductCategoryOperationException 商品类别操作异常包装类
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {

        //==== 1. 解除商品表和商品类别表的关联 ====
        try {
            int effect = productDao.updateProductCategoryToNull(productCategoryId);
            if (effect < 0) {
                throw new ProductOperationException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new ProductOperationException("删除商品类别失败" + e.toString());
        }

        //==== 2. 执行删除 ====
        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (ProductCategoryOperationException e) {
            throw new ProductCategoryOperationException("deleteProductCategory error：" + e.getMessage());
        }

    }
}
