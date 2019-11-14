package com.changyue.o2o.service;

import com.changyue.o2o.dto.ShopExecution;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.Shop;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-17 19:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopServiceTest  {

    @Autowired
    private ShopService shopService;


    @Test
    public void testGetShopList() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shop.setOwner(owner);

        ShopExecution shopList = shopService.getShopList(shop, 1, 2);

        PageInfo<Shop> shopPageInfo = shopList.getShopPageInfo();
        shopPageInfo.getList().forEach(page -> {
            System.out.println(page);
            System.out.println();
        });

    }

 /*   @Test
    public void modify() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("modify shop");
        File file = new File("C:/Users/Administrator/Desktop/xing.png");
        InputStream inputStream = new FileInputStream(file);
        ShopExecution shopExecution = shopService.modifyShop(shop, inputStream, "xing.png");
        System.out.println(shopExecution.getShop().getShopImg());
    }

    @Test
    public void testAddShop() throws FileNotFoundException {
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

        shop.setShopName("测试332");
        shop.setShopAddr("test");
        shop.setShopDesc("test");
        shop.setPhone("123123123");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(null);
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File file = new File("D:\\wallpaper\\samuel.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        ShopExecution shopExecution = shopService.addShop(shop, fileInputStream, file.getName());
        Assert.assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }*/


}
