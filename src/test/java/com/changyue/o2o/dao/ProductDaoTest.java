package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.Product;
import com.changyue.o2o.entity.ProductCategory;
import com.changyue.o2o.entity.Shop;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-10 16:30
 */
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void testAAddProduct() {
        Shop shop = new Shop();
        shop.setShopId(19L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);
        Product product = new Product();
        product.setProductName("test");
        product.setProductDesc("test");
        product.setNormalPrice("test");
        product.setPromotionPrice("23");
        product.setEnableStatus(1);
        product.setImgAddr("test");
        product.setPriority(2);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setProductCategory(productCategory);

        int effect = productDao.insertProduct(product);
        Assert.assertEquals(effect, 1);

    }

    @Test
    public void testQueryProductById() {
        Product product = productDao.queryProductById(16L);
        System.out.println(product);
    }

    @Test
    public void testUpdateProductCategoryIdToNull() {
        productDao.updateProductCategoryToNull(11L);
    }

    @Test
    public void testUpdateProduct() {
        Shop shop = new Shop();
        shop.setShopId(19L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);
        Product product = new Product();
        product.setProductId(18L);
        product.setProductName("test2");
        product.setProductDesc("test2");
        product.setNormalPrice("2");
        product.setPromotionPrice("1");
        product.setImgAddr("test");
        product.setPriority(2);
        product.setLastEditTime(new Date());
        product.setShop(shop);
        product.setProductCategory(productCategory);
        int i = productDao.updateProduct(product);
    }

    @Test
    public void testQueryForList() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(19L);
        product.setShop(shop);

        List<Product> productList = productDao.queryProductList(product, 1, 99);
        productList.forEach(System.out::println);
    }

}
