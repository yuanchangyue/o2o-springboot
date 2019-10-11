package com.changyue.o2o.service;

import com.changyue.o2o.dto.WechatAuthExecution;
import com.changyue.o2o.entity.WechatAuth;
import com.changyue.o2o.exceptions.WechtAuthOperationException;

public interface WechatAuthService {


    /**
     * 通过openId查询微信认证信息
     *
     * @param openId openId
     * @return 微信认证信息
     */
    WechatAuth getWechatAuthByOpenId(String openId);


    /**
     * 注册微信认证信息
     *
     * @param wechatAuth 微信认证信息
     * @return 微信认证信息包装类
     */
    WechatAuthExecution registerWechat(WechatAuth wechatAuth) throws WechtAuthOperationException;

}
