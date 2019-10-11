package com.changyue.o2o.config.redis;

import com.changyue.o2o.cache.JedisPoolWriper;
import com.changyue.o2o.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: o2o-springboot
 * @description: redis 配置类
 * @author: YuanChangYue
 * @create: 2019-10-09 23:20
 */
@Configuration
public class RedisConfiguration {

    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.port}")
    private String port;
    @Value("${redis.database}")
    private String database;
    @Value("${redis.pool.maxActive}")
    private String maxActive;
    @Value("${redis.pool.maxIdle}")
    private String maxIdle;
    @Value("${redis.pool.maxWait}")
    private String maxWait;
    @Value("${redis.pool.testOnBorrow}")
    private String testOnBorrow;

    @Autowired
    private JedisPoolWriper jedisWritePool;
    @Autowired
    private JedisPoolConfig jedisPoolConfig;
    @Autowired
    private JedisUtil jedisUtil;











}
