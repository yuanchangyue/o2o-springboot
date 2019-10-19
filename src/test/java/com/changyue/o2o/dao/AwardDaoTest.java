package com.changyue.o2o.dao;

import com.changyue.o2o.entity.Award;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @program: o2o-springboot
 * @description:
 * @author: 袁阊越
 * @create: 2019-10-15 13:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardDaoTest {
    @Autowired
    private AwardDao awardDao;

    @Test
    public void testInsertAward() {
        long shopId = 19;
        Award award = new Award();
        award.setAwardName("测试3");
        award.setAwardDesc("测试奖品描述3");
        award.setAwardImg("测试 img 3");
        award.setCreateTime(new Date());
        award.setLastEditTime(new Date());
        award.setPoint(12);
        award.setEnableStatus(1);
        award.setPriority(2);
        award.setShopId(shopId);
        awardDao.insertAward(award);
    }

    @Test
    public void testQueryForList() {
        List<Award> awards = awardDao.queryAwardList(new Award(), 0, 6);
        awards.forEach(System.out::println);
        List<Award> awards1 = awardDao.queryAwardAllList(new Award());
        awards1.forEach(System.out::println);
        Assert.assertEquals(2, awards.size());
    }

    @Test
    public void testUpdateAward() {
        Award award = new Award();
        award.setAwardName("123");
        award.setAwardDesc("123 !!!!!");
        award.setAwardImg("123 img");
        award.setPriority(3);
        award.setPoint(3);
        award.setLastEditTime(new Date());
        award.setAwardId(2L);
        award.setShopId(19L);

        awardDao.updateAward(award);

    }

    @Test
    public void testQueryById() {
        Award award = awardDao.queryAwardById(2L);
        System.out.println(award);
    }

    @Test
    public void testDelete() {
        int i = awardDao.deleteAward(2L, 19L);
        Assert.assertEquals(i, 1);
    }

}
