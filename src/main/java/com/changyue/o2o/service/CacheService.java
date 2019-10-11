package com.changyue.o2o.service;

/**
 * @program: o2o
 * @description: Redis缓存service
 * @author: YuanChangYue
 * @create: 2019-09-11 22:01
 */
public interface CacheService {

    /**
     * 根据key的前缀删除key-values的值
     *
     * @param keyPer key前缀
     */
    void removeFromCache(String keyPer);
}
