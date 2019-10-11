package com.changyue.o2o.config.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @program: o2o-springboot
 * @description: spring web 配置类 开启Mvc自动注入spring容器
 * @author: 袁阊越
 * @create: 2019-10-11 21:39
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * 获得容器
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 处理静态资源
     * <mvc:resources mapping="/resources/assets/js/**" location="/resources/assets/js/"/>
     * <mvc:resources mapping="/resources/assets/css/**" location="/resources/assets/css/"/>
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/assets/js/**").addResourceLocations("/resources/assets/js/");
        registry.addResourceHandler("/resources/assets/css/**").addResourceLocations("/resources/assets/css/");
    }

    /**
     * 默认的请求处理器 <mvc:default-servlet-handler/>
     *
     * @param configurer configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 创建视图解析器
     *
     * @return 视图解析器
     */
    @Bean("viewResolver")
    public InternalResourceViewResolver createInternalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setApplicationContext(applicationContext);
        viewResolver.setCache(false);
        viewResolver.setSuffix(".html");
        viewResolver.setPrefix("/WEB-INF/html/");
        return viewResolver;
    }

    /**
     * 文件上传
     * <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
     * <property name="defaultEncoding" value="utf-8"/>
     * <!--上传的大小 20M-->
     * <property name="maxUploadSize" value="20971520"/>
     * <property name="maxInMemorySize" value="20971520"/>
     * </bean>
     *
     * @return CommonsMultipartResolver
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver createCommonsMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(20971520);
        resolver.setMaxInMemorySize(20971520);
        return resolver;
    }

}














