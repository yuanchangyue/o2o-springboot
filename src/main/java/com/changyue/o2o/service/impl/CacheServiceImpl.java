package com.changyue.o2o.service.impl;

import com.changyue.o2o.cache.JedisUtil;
import com.changyue.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @program: o2o
 * @description: 缓存service的实现类
 * @author: YuanChangYue
 * @create: 2019-09-11 22:04
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys jedisKeys;

    /**
     * @param keyPer key前缀
     */
    @Override
    public void removeFromCache(String keyPer) {
        Set<String> keys = jedisKeys.keys(keyPer + "*");
        for (String key : keys) {
            jedisKeys.del(key);
        }
    }
}
