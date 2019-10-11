package com.changyue.o2o.dao;

import com.changyue.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @program: o2o
 * @description: 平台账号
 * @author: YuanChangYue
 * @create: 2019-09-24 21:04
 */
public interface LocalAuthDao {

    /**
     * 通过用户名和密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 本地
     */
    LocalAuth queryLocalByUserNameAndPw(@Param("username") String username, @Param("password") String password);


    /**
     * 通过用户id查找本地
     *
     * @param userId 用户id
     * @return 本地
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    /**
     * 添加平添账号
     *
     * @param localAuth 平台账号信息
     * @return 影响行数
     */
    int insertLocalAuth(LocalAuth localAuth);


    /**
     * 修改平台账号信息
     *
     * @param userId       用户id
     * @param username     用户名
     * @param password     密码
     * @param newPassword  新密码
     * @param lastEditTime 最后的修改时间
     * @return 影响行数
     */
    int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username, @Param("password") String password,
                        @Param("newPassword") String newPassword, @Param("lastEditTime") Date lastEditTime);


}
