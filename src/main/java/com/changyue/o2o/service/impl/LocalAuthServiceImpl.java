package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.LocalAuthDao;
import com.changyue.o2o.dto.LocalAuthExecution;
import com.changyue.o2o.emums.LocalAuthStateEnum;
import com.changyue.o2o.entity.LocalAuth;
import com.changyue.o2o.exceptions.LocalAuthOperationException;
import com.changyue.o2o.service.LocalAuthService;
import com.changyue.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: o2o
 * @description: 平台认证业务实现类
 * @author: YuanChangYue
 * @create: 2019-09-24 22:17
 */
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalByUserNameAndPw(String username, String password) {
        return localAuthDao.queryLocalByUserNameAndPw(username, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalByUserId(Long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {

        if ((localAuth == null) || (localAuth.getPersonInfo() == null) || (localAuth.getUsername() == null) || (localAuth.getPassword() == null)
                || (localAuth.getPersonInfo().getUserId() == 0)) {
            return new LocalAuthExecution(LocalAuthStateEnum.LOCAL_AUTH_NULL);
        }

        //确保没有重复的账号
        LocalAuth tempLocalAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if (tempLocalAuth != null) {
            return new LocalAuthExecution(LocalAuthStateEnum.LOCAL_AUTH_REPEAT);
        }

        //绑定账号
        try {

            localAuth.setLastEditTime(new Date());
            localAuth.setCreateTime(new Date());
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));

            int effect = localAuthDao.insertLocalAuth(localAuth);
            if (effect <= 0) {
                throw new LocalAuthOperationException("账号绑定失败!");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.LOCAL_AUTH_CREATE_SUCCESS, localAuth);
            }
        } catch (Exception e) {
            throw new LocalAuthOperationException("账号绑定失败:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword) throws LocalAuthOperationException {
        if (userId != null && username != null && password != null && newPassword != null) {
            try {
                int effect = localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
                if (effect <= 0) {
                    throw new LocalAuthOperationException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.LOCAL_AUTH_UPDATE_PASSWORD_SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败");
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.LOCAL_AUTH_UPDATE_INFO_NULL);
        }
    }

}
