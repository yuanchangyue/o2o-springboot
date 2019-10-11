package com.changyue.o2o.dao;

import com.changyue.o2o.entity.WechatAuth;
import org.springframework.stereotype.Repository;

/**
 * @program: o2o
 * @description: 微信验证信息
 * @author: YuanChangYue
 * @create: 2019-09-24 12:59
 */
@Repository
public interface WechatAuthDao {

    /**
     * 通过openId查询微信账号信息
     *
     * @param openId openId
     * @return 微信账号信息
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    /**
     * 插入微信账号信息
     *
     * @param wechatAuth 微信认证信息
     * @return 影响
     */
    int insertWechatAuth(WechatAuth wechatAuth);

}
