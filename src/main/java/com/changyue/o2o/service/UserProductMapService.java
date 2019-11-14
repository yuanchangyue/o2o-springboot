package com.changyue.o2o.service;

import com.changyue.o2o.dto.UserProductMapExecution;
import com.changyue.o2o.entity.UserProductMap;

/**
 * @program: o2o-springboot
 * @description: 用户消费商品服务层
 * @author: 袁阊越
 * @create: 2019-11-02 00:02
 */
public interface UserProductMapService {

    /**
     * 通过条件记录查询分页的信息记录
     *
     * @param userProductCondition 条件
     * @param pageIndex            从那一页开始
     * @param pageSize             一页中的数量
     * @return 包装类
     */
    UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize);


}
