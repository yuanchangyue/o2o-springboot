package com.changyue.o2o.config.dao;

import com.changyue.o2o.util.DESUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * @program: o2o-springboot
 * @description: 数据源配置到IOC容器中
 * @author: YuanChangYue
 * @create: 2019-10-09 21:24
 */
@Configuration
@MapperScan("com.changyue.o2o.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 配置dataSource
     *
     * @return ComboPooledDataSource
     */
    @Bean(name = "dataSource")
    public ComboPooledDataSource createDateSource() {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(jdbcDriver);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(DESUtils.getDecryptString(username));
            dataSource.setPassword(DESUtils.getDecryptString(password));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
