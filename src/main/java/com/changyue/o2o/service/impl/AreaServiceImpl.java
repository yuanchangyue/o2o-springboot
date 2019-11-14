package com.changyue.o2o.service.impl;

import com.changyue.o2o.cache.JedisUtil;
import com.changyue.o2o.dao.AreaDao;
import com.changyue.o2o.entity.Area;
import com.changyue.o2o.exceptions.AreaOperationException;
import com.changyue.o2o.service.AreaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: shopping
 * @description: 区域service的实现类
 * @author: ChangYue
 * @create: 2019-03-12 21:20
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private JedisUtil.Keys jedisKey;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    /**
     * 获取区域信息
     * 使用Redis缓存存放区域信息
     *
     * @return 区域信息
     */
    @Override
    @Transactional
    public List<Area> getAreaList() {
        String key = AREA_LIST_KEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKey.exists(key)) {
            areaList = areaDao.queryAll();
            String jsonString = null;
            try {
                //使用jackson将areaList转换为String
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
            //将转换的areaList的String存进redis的键值对
            jedisStrings.set(key, jsonString);
        } else {
            //取出已存在的areaList
            String jsonString = jedisStrings.get(key);
            //需要将取出的String转换的类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                //进行转换
                areaList = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }
        }
        return areaList;
    }
}
