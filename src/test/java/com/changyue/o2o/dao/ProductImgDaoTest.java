package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.ProductImg;
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
 * @create: 2019-04-10 16:21
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testABatchAddProductImg() {

        ProductImg productImg = new ProductImg();
        productImg.setImgAddr("img1");
        productImg.setImgDesc("img1 desc");
        productImg.setPriority(1);
        productImg.setCreateTime(new Date());
        productImg.setProductId(4L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("img2");
        productImg2.setImgDesc("img2 desc");
        productImg2.setPriority(2);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(4L);

        ArrayList<ProductImg> list = new ArrayList<>();
        list.add(productImg);
        list.add(productImg2);

        int i = productImgDao.batchInsertProductImg(list);

        Assert.assertEquals(i, 2);
    }


    @Test
    public void testCProductImgList() {

        List<ProductImg> productImgs = productImgDao.queryProductImgList(16L);

//        productImgs.forEach(productImg -> {
//            System.out.println("productImg = " + productImg);
//        });

    }

    @Test
    public void testBDeleteProductImg() {
        int effect = productImgDao.deleteProductImgByProductId(17L);
        Assert.assertEquals(effect, 2);
    }


}
