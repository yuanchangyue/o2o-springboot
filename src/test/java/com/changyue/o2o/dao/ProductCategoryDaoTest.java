package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.ProductCategory;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-25 22:33
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao categoryDao;

    @Test
    public void testBQueryByShopId(){
        long shopId = 1;
        List<ProductCategory> productCategories = categoryDao.queryProductCategoryList(shopId);
        System.out.println(productCategories.size());
    }


    @Test
    public void testABatchInsert(){
        ProductCategory p1 = new ProductCategory();
        p1.setCreateTime(new Date());
        p1.setPriority(5);
        p1.setShopId(10L);
        p1.setProductCategoryName("test1");
        ProductCategory p2= new ProductCategory();
        p2.setCreateTime(new Date());
        p2.setPriority(6);
        p2.setShopId(10L);
        p2.setProductCategoryName("test2");

        ArrayList<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(p1);
        productCategories.add(p2);

        int effectNum = categoryDao.batchInsertProductCategory(productCategories);
        Assert.assertEquals(effectNum,2);
    }

    @Test
    public void testCDeleteProductCategory(){
        long shopId = 10;
        List<ProductCategory> productCategories = categoryDao.queryProductCategoryList(shopId);
        for (ProductCategory productCategory : productCategories) {

            if ("test1".equals(productCategory.getProductCategoryName())||"test2".equals(productCategory.getProductCategoryName())) {
                int i = categoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
                Assert.assertEquals(i,1);
            }
        }

    }

}
