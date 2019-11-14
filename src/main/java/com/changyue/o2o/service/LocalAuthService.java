package com.changyue.o2o.service;

import com.changyue.o2o.dto.LocalAuthExecution;
import com.changyue.o2o.entity.LocalAuth;
import com.changyue.o2o.exceptions.LocalAuthOperationException;

/**
 * @program: o2o
 * @description: 平台账号业务接口
 * @author: YuanChangYue
 * @create: 2019-09-24 22:00
 */
public interface LocalAuthService {

    /**
     * 通过用户名和密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 本地
     */
    LocalAuth getLocalByUserNameAndPw(String username, String password);


    /**
     * 通过用户id查找本地
     *
     * @param userId 用户id
     * @return 本地
     */
    LocalAuth getLocalByUserId(Long userId);


    /**
     * 绑定微信账号,生成平台账号
     *
     * @param localAuth 平台信息
     * @return 平台执行包装类
     * @throws LocalAuthOperationException 平台操作异常类
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;


    /**
     * 修改平台信息
     *
     * @param userId       用户id
     * @param username     用户名
     * @param password     密码
     * @param newPassword  新密码
     * @return 平台执行包装类
     * @throws LocalAuthOperationException 平台操作异常类
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password,
                                       String newPassword) throws LocalAuthOperationException;

}
