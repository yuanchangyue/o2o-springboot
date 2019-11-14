package com.changyue.o2o.service.impl;

import com.changyue.o2o.dao.UserAwardMapDao;
import com.changyue.o2o.dao.UserShopMapDao;
import com.changyue.o2o.dto.UserAwardMapExecution;
import com.changyue.o2o.emums.UserAwardMapStateEnum;
import com.changyue.o2o.entity.UserAwardMap;
import com.changyue.o2o.entity.UserShopMap;
import com.changyue.o2o.service.UserAwardMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {

    @Autowired
    private UserAwardMapDao userAwardMapDao;
    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserAwardMapExecution listUserAwardMap(
            UserAwardMap userAwardCondition, Integer pageIndex, Integer pageSize) {
        if (userAwardCondition != null && pageIndex != null && pageSize != null) {

            PageHelper.startPage(pageIndex, pageSize);
            List<UserAwardMap> userAwardMapList = userAwardMapDao
                    .queryUserAwardMapALLList(userAwardCondition);
            PageInfo<UserAwardMap> userAwardMapPageInfo = new PageInfo<>(userAwardMapList, 5);

            UserAwardMapExecution ue = new UserAwardMapExecution();
            ue.setUserAwardMapPageInfo(userAwardMapPageInfo);
            return ue;
        } else {
            return null;
        }
    }

    @Override
    public UserAwardMap getUserAwardMapById(long userAwardMapId) {
        return userAwardMapDao.queryUserAwardMapById(userAwardMapId);
    }

    @Override
    @Transactional
    public UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap)
            throws RuntimeException {
        if (userAwardMap != null && userAwardMap.getUser().getUserId() != null
                && userAwardMap.getShop().getShopId() != null) {
            userAwardMap.setCreateTime(new Date());
            userAwardMap.setUsedStatus(0);
            try {
                int effectedNum = 0;
                if (userAwardMap.getPoint() != null
                        && userAwardMap.getPoint() > 0) {
                    UserShopMap userShopMap = userShopMapDao.selectUserShopMapById(
                            userAwardMap.getUser().getUserId(), userAwardMap.getShop().getShopId());
                    if (userShopMap != null) {
                        if (userShopMap.getPoint() >= userAwardMap.getPoint()) {
                            userShopMap.setPoint(userShopMap.getPoint()
                                    - userAwardMap.getPoint());
                            effectedNum = userShopMapDao
                                    .updateUserShopMap(userShopMap);
                            if (effectedNum <= 0) {
                                throw new RuntimeException("更新积分信息失败");
                            }
                        } else {
                            throw new RuntimeException("积分不足无法领取");
                        }
                    } else {
                        // 在店铺没有积分
                        throw new RuntimeException("在本店铺没有积分，无法对换奖品");
                    }
                }
                effectedNum = userAwardMapDao.insertUserAwardMap(userAwardMap);
                if (effectedNum <= 0) {
                    throw new RuntimeException("领取奖励失败");
                }
                return new UserAwardMapExecution(UserAwardMapStateEnum.SUCCESS,
                        userAwardMap);
            } catch (Exception e) {
                throw new RuntimeException("领取奖励失败:" + e.toString());
            }
        } else {
            return new UserAwardMapExecution(
                    UserAwardMapStateEnum.NULL_USERAWARD_INFO);
        }
    }

    @Override
    @Transactional
    public UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap)
            throws RuntimeException {
        if (userAwardMap == null || userAwardMap.getUserAwardId() == null
                || userAwardMap.getUsedStatus() == null) {
            return new UserAwardMapExecution(
                    UserAwardMapStateEnum.NULL_USERAWARD_ID);
        } else {
            try {
                int effectedNum = userAwardMapDao
                        .updateUserAwardMap(userAwardMap);
                if (effectedNum <= 0) {
                    return new UserAwardMapExecution(
                            UserAwardMapStateEnum.INNER_ERROR);
                } else {
                    return new UserAwardMapExecution(
                            UserAwardMapStateEnum.SUCCESS, userAwardMap);
                }
            } catch (Exception e) {
                throw new RuntimeException("modifyUserAwardMap error: "
                        + e.getMessage());
            }
        }
    }

}
