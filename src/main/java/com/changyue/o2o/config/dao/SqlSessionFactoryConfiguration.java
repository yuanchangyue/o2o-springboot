package com.changyue.o2o.config.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @program: o2o-springboot
 * @description: SqlSessionFactoryBean 配置类
 * @author: YuanChangYue
 * @create: 2019-10-09 21:39
 */
@Configuration
public class SqlSessionFactoryConfiguration {

    @Autowired
    private DataSource dataSource;

    /**
     * mybatis-config的文件名称
     */
    private String myBatisConfigurationXml;

    @Value("${mybatis_config_file}")
    public void setMyBatisConfigurationXml(String myBatisConfigurationXml) {
        this.myBatisConfigurationXml = myBatisConfigurationXml;
    }

    /**
     * MyBatis mapper文件的路径
     */
    private String mapperPath;

    @Value("${mapper_path}")
    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    /**
     * 实体类所在package路径
     */
    private String typeAliasPackage;

    @Value("${type_alias_package}")
    public void setTypeAliasPackage(String typeAliasPackage) {
        this.typeAliasPackage = typeAliasPackage;
    }

    /**
     * 初始化SqlSessionFactoryBean
     * 配置MyBatis configuration的设置文件
     * 配置mapper文件映射路径
     * 配置数据源
     *
     * @return SqlSessionFactoryBean
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource(myBatisConfigurationXml));
        //扫描路径
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
        sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources(packageSearchPath));
        sqlSessionFactory.setTypeAliasesPackage(typeAliasPackage);

        return sqlSessionFactory;
    }
}
