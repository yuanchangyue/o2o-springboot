package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.PersonInfoDao;
import com.changyue.o2o.dao.WechatAuthDao;
import com.changyue.o2o.dto.WechatAuthExecution;
import com.changyue.o2o.emums.WechatAuthStateEnum;
import com.changyue.o2o.entity.PersonInfo;
import com.changyue.o2o.entity.WechatAuth;
import com.changyue.o2o.exceptions.WechtAuthOperationException;
import com.changyue.o2o.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: o2o
 * @description: 微信认证业务实现类
 * @author: YuanChangYue
 * @create: 2019-09-24 14:46
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

    private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);

    @Autowired
    private PersonInfoDao personInfoDao;

    @Autowired
    private WechatAuthDao wechatAuthDao;

    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }

    @Override
    @Transactional
    public WechatAuthExecution registerWechat(WechatAuth wechatAuth) throws WechtAuthOperationException {
        if (wechatAuth == null || wechatAuth.getOpenId() == null) {
            return new WechatAuthExecution(WechatAuthStateEnum.WECHAT_AUTH_NULL);
        }

        try {
            wechatAuth.setCreateTime(new Date());
            //微信账号有用户信息，没有userId 就是没有进过数据库(第一次使用微信登录)
            //自动创建用户信息
            if ((wechatAuth.getPersonInfo() != null) && (wechatAuth.getPersonInfo().getUserId() <= 0)) {
                try {
                    wechatAuth.getPersonInfo().setCreateTime(new Date());
                    wechatAuth.getPersonInfo().setEnableStatus(1);
                    PersonInfo personInfo = wechatAuth.getPersonInfo();
                    int effect = personInfoDao.insertPersonInf(personInfo);
                    wechatAuth.setPersonInfo(personInfo);
                    if (effect <= 0) {
                        throw new WechtAuthOperationException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    log.error("添加用户错误" + e.getMessage());
                    throw new WechtAuthOperationException("添加用户错误" + e.getMessage());
                }
            }

            int effect = wechatAuthDao.insertWechatAuth(wechatAuth);
            if (effect <= 0) {
                throw new WechtAuthOperationException("微信账号创建失败");
            } else {
                return new WechatAuthExecution(WechatAuthStateEnum.WECHAT_AUTH_SUCCESS, wechatAuth);
            }
        } catch (Exception e) {
            log.error("微信账号创建失败:" + e.getMessage());
            throw new WechtAuthOperationException("微信账号创建失败:" + e.getMessage());
        }

    }


}
