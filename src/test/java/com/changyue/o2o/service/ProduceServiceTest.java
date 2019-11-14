package com.changyue.o2o.service;

import com.changyue.o2o.dao.ProductDao;
import com.changyue.o2o.dto.ImageHolder;
import com.changyue.o2o.dto.ProductExecution;
import com.changyue.o2o.emums.ProductStateEnum;
import com.changyue.o2o.entity.Product;
import com.changyue.o2o.entity.ProductCategory;
import com.changyue.o2o.entity.Shop;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-04-10 17:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduceServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;


    @Test
    public void testAddProduct() throws IOException {

        Product product = new Product();

        Shop shop = new Shop();
        shop.setShopId(10L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("test222");
        product.setProductDesc("test");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        File file = new File("C:/Users/Administrator/Desktop/samuel.jpg");
        File file2 = new File("C:/Users/Administrator/Desktop/xing.png");

        FileInputStream inputStream = new FileInputStream(file);
        FileInputStream inputStream2 = new FileInputStream(file2);

        ImageHolder imageHolder1 = new ImageHolder(file.getName(), inputStream);
        ImageHolder imageHolder2 = new ImageHolder(file2.getName(), inputStream2);

        ArrayList<ImageHolder> imageHolders = new ArrayList<>();
        imageHolders.add(imageHolder1);
        imageHolders.add(imageHolder2);

        ProductExecution productExecution = productService.addProduct(product, imageHolder1, imageHolders);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());


    }

    @Test
    public void testModifyProduct() throws IOException {

        Product product = new Product();

        Shop shop = new Shop();
        shop.setShopId(30L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(17L);

        product.setProductId(16L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("new test2");
        product.setProductDesc("new desc2");
        product.setPriority(2);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);

        File file = new File("C:/Users/Administrator/Desktop/a.jpg");
        File file2 = new File("C:/Users/Administrator/Desktop/a.jpg");

        FileInputStream inputStream = new FileInputStream(file);
        FileInputStream inputStream2 = new FileInputStream(file2);

        ImageHolder imageHolder1 = new ImageHolder(file.getName(), inputStream);
        ImageHolder imageHolder2 = new ImageHolder(file2.getName(), inputStream2);

        ArrayList<ImageHolder> imageHolders = new ArrayList<>();
        imageHolders.add(imageHolder1);
        imageHolders.add(imageHolder2);

        ProductExecution productExecution = productService.modifyProduct(product, imageHolder1, imageHolders);

        System.out.println("productExecution = " + productExecution);


    }

    @Test
    public void testQueryProductList() {

        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(19L);
        product.setShop(shop);

        ProductExecution productList = productService.getProductList(product, 0, 1);
        PageInfo<Product> productPageInfo = productList.getProductPageInfo();

        System.out.println(productPageInfo);
        productPageInfo.getList().forEach(product1 -> {
            System.out.println(product1);
            System.out.println();
        });

/*        List<Product> products = productDao.queryProductListByPageHelper(product);
        products.forEach(System.out::println);*/

    }
}
