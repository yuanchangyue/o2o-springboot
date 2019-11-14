package com.changyue.o2o.service.impl;

import com.changyue.o2o.cache.JedisUtil;
import com.changyue.o2o.dao.HeadLineMapper;
import com.changyue.o2o.entity.HeadLine;
import com.changyue.o2o.exceptions.HeadLineOperationException;
import com.changyue.o2o.service.HeadLineService;
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
 * @program: ShoppingWithSSMAndSpringBoot
 * @description: 头条service的实现类
 * @author: ChangYue
 * @create: 2019-08-02 19:25
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineMapper headLineMapper;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Autowired
    private JedisUtil.Keys jedisKey;


    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);

    /**
     * 获得HeadLine的list并存放在redis中
     *
     * @param headLine 携带条件headline
     * @return headlines 满足条件的headline的list
     */
    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLine) {
        String key = HAED_LINE_KEK;
        List<HeadLine> headLines = null;
        ObjectMapper mapper = new ObjectMapper();

        //判断头条信息是否需要显示
        if (headLine != null && headLine.getEnableStatus() != null) {
            key = key + "_" + headLine.getEnableStatus();
        }
        headLines = headLineMapper.selectAllHeadLine(headLine);
        if (!jedisKey.exists(key)) {
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(headLines);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
            //将headLines加入redis的键值对中
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                headLines = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadLineOperationException(e.getMessage());
            }
        }
        return headLines;
    }

   /* @Override
    public List<HeadLine> getHeadLineList(HeadLine headLine) {
        return headLineMapper.selectAllHeadLine(headLine);
    }*/
}
