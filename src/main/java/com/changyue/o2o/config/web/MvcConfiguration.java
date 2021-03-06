package com.changyue.o2o.config.web;

import com.changyue.o2o.interceptor.ShopLoginInterceptor;
import com.changyue.o2o.interceptor.ShopPermissionInterceptor;
import com.changyue.o2o.util.ImageUtil;
import com.changyue.o2o.util.PathUtils;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @program: o2o-springboot
 * @description: spring web 配置类 开启Mvc自动注入spring容器
 * @author: 袁阊越
 * @create: 2019-10-11 21:39
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

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
     * /Users/baidu/work/image/upload/
     * D:/projectdev/img/upload/
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       /* registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        registry.addResourceHandler("/resources/assets/js/**").addResourceLocations("/resources/assets/js/");
        registry.addResourceHandler("/resources/assets/css/**").addResourceLocations("/resources/assets/css/");*/
<<<<<<< HEAD
        //spring boot 内置tomcat 不能设置server.xml配置docBase 图片的路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/Users/baidu/work/image/upload/");
=======

        //spring boot 内置tomcat 不能设置server.xml配置docBase 图片的路径

        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + PathUtils.getImgBasePath() + "/upload/");

>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
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

<<<<<<< HEAD
=======

>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5
    /**
     * 创建视图解析器
     *
     * @return 视图解析器
     */
    @Bean("defaultViewResolver")
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

    /**
     * 拦截器
     * <p>
     * <mvc:mapping path="/shopadmin/**"/>
     * <p>
     * <!--商铺列表-->
     * <mvc:exclude-mapping path="/shopadmin/shoplist"/>
     * <mvc:exclude-mapping path="/shopadmin/getshoplist"/>
     * <p>
     * <!--商铺注册-->
     * <mvc:exclude-mapping path="/shopadmin/getshopinitinfo"/>
     * <mvc:exclude-mapping path="/shopadmin/shopoperation"/>
     * <mvc:exclude-mapping path="/shopadmin/registershop"/>
     * <p>
     * <!--商铺管理-->
     * <mvc:exclude-mapping path="/shopadmin/shopmanagement"/>
     * <mvc:exclude-mapping path="/shopadmin/getshopmanagementinfo"/>
     * <p>
     * <mvc:exclude-mapping path="/shopadmin/getuser"/>
     *
     * <bean id="shopPermissionInterceptor" class="com.changyue.o2o.interceptor.ShopPermissionInterceptor"/>
     *
     * @param registry 测试器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        //登录拦截器
        InterceptorRegistration loginInterceptor = registry.addInterceptor(new ShopLoginInterceptor());
        loginInterceptor.addPathPatterns(interceptPath);
        //二维码
        loginInterceptor.excludePathPatterns("/shopadmin/addshopauthmap");
        //统计日销量
        loginInterceptor.excludePathPatterns("/shopadmin/listproductselldailyinfobyshop");
        loginInterceptor.excludePathPatterns("/shopadmin/listuserproductmapbyshop");
        //权限拦截器
        InterceptorRegistration permissionInterceptor = registry.addInterceptor(new ShopPermissionInterceptor());
        permissionInterceptor.addPathPatterns(interceptPath);

        //商铺列表
        permissionInterceptor.excludePathPatterns("/shopadmin/shoplist");
        permissionInterceptor.excludePathPatterns("/shopadmin/getshoplist");
        //商铺注册
        permissionInterceptor.excludePathPatterns("/shopadmin/getshopinitinfo");
        permissionInterceptor.excludePathPatterns("/shopadmin/shopoperation");
        permissionInterceptor.excludePathPatterns("/shopadmin/registershop");
        //商铺管理
        permissionInterceptor.excludePathPatterns("/shopadmin/shopmanagement");
        permissionInterceptor.excludePathPatterns("/shopadmin/getshopmanagementinfo");
        permissionInterceptor.excludePathPatterns("/shopadmin/getuser");
        //二维码
        permissionInterceptor.excludePathPatterns("/shopadmin/addshopauthmap");
<<<<<<< HEAD
        permissionInterceptor.excludePathPatterns("/shopadmin/listusershopmapsbyshop");
=======
>>>>>>> 4084400726203979b8a84d9e1a5b40f88ba6a7c5


    }

    @Value("${kaptcha.border}")
    private String border;

    @Value("${kaptcha.textproducer.font.color}")
    private String fontColor;

    @Value("${kaptcha.textproducer.font.names}")
    private String fontName;

    @Value("${kaptcha.img.width}")
    private String imgSize;

    @Value("${kaptcha.img.height}")
    private String imgHeight;

    @Value("${kaptcha.textproducer.char.string}")
    private String charString;

    @Value("${kaptcha.textproducer.font.size}")
    private String fontSize;

    @Value("${kaptcha.noise.color}")
    private String noiseColor;

    @Value("${kaptcha.textproducer.char.length}")
    private String charLength;


    /**
     * 验证码的servlet
     *
     * @return servlet
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        ServletRegistrationBean<KaptchaServlet> servlet = new ServletRegistrationBean<>(new KaptchaServlet(), "/Kaptcha");
        servlet.addInitParameter("kaptcha.border", border);
        servlet.addInitParameter("kaptcha.textproducer.font.color", fontColor);
        servlet.addInitParameter("kaptcha.textproducer.font.names", fontName);
        servlet.addInitParameter("kaptcha.img.width", imgSize);
        servlet.addInitParameter("kaptcha.img.height", imgHeight);
        servlet.addInitParameter("kaptcha.textproducer.char.string", charString);
        servlet.addInitParameter("kaptcha.textproducer.font.size", fontSize);
        servlet.addInitParameter("kaptcha.noise.color", noiseColor);
        servlet.addInitParameter("kaptcha.textproducer.char.length", charLength);

        return servlet;
    }

}














