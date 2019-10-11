package com.changyue.o2o.dao;

import com.changyue.o2o.BaseTest;
import com.changyue.o2o.entity.Area;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Shop;
import com.changyue.o2o.entity.ShopCategory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-13 01:49
 */
public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;


    @Test
    public void queryShopListByPageHelper() {
        Shop shop = new Shop();

        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shop.setOwner(owner);

        PageHelper.startPage(2, 3);

        List<Shop> shops = shopDao.queryShopListByAll(shop);

        //shops.forEach(System.out::println);

        PageInfo<Shop> page = new PageInfo<Shop>(shops);

        page.getList().forEach(s -> {
            System.out.println(s);
            System.out.println();
        });


    }

    @Test
    public void queryShopList() {
        Shop shop = new Shop();

        ShopCategory parent = new ShopCategory();
        ShopCategory child = new ShopCategory();

        parent.setShopCategoryId(10L);
        child.setParent(parent);

        shop.setShopCategory(child);

        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shop.setOwner(owner);

        List<Shop> shops = shopDao.queryShopList(shop, 0, 6);

        System.out.println("shop = " + shop);

        shops.forEach(System.out::println);

        int i = shopDao.queryShopCount(shop);
        System.out.println(shops.size());
        System.out.println(i);

    }


    @Test
    @Ignore
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        personInfo.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);

        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setOwner(personInfo);

        shop.setShopName("测试");
        shop.setShopAddr("test");
        shop.setShopDesc("test");
        shop.setPhone("123123123");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(null);
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int effect = shopDao.insertShop(shop);

        Assert.assertEquals(1, effect);


    }



/*


    @Test
    public void testQueryById() {
        long shopId = 1;
        Shop shop = shopDao.queryById(shopId);
        System.out.println(shop);
    }

    @Test
    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("测试ing");
        shop.setShopAddr("test");
        shop.setLastEditTime(new Date());
        int i = shopDao.updateShop(shop);
        Assert.assertEquals(1, i);
    }
*/


}
