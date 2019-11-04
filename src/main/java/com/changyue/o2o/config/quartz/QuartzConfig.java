package com.changyue.o2o.config.quartz;

import com.changyue.o2o.service.ProductSellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @program: o2o-springboot
 * @description: Quartz定时调度配置
 * @author: 袁阊越
 * @create: 2019-10-24 14:29
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private ProductSellDailyService productSellDailyService;

    @Autowired
    private MethodInvokingJobDetailFactoryBean jobDetailsFactory;

    @Autowired
    private CronTriggerFactoryBean productSellDailyTriggerFactoryBean;


    /**
     * 通过MethodInvokingJobDetailFactoryBean 创建一个任务
     *
     * @return MethodInvokingJobDetailFactoryBean
     */
    @Bean("jobDetailsFactory")
    public MethodInvokingJobDetailFactoryBean createJobDetails() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setName("product_sell_detail_job");
        methodInvokingJobDetailFactoryBean.setGroup("job_product_sell_details_group");
        //是否并发执行job false 一个一个执行
        methodInvokingJobDetailFactoryBean.setConcurrent(false);
        //设置目标对象
        methodInvokingJobDetailFactoryBean.setTargetObject(productSellDailyService);
        //设置目标方法
        methodInvokingJobDetailFactoryBean.setTargetMethod("dailyCalculate");
        return methodInvokingJobDetailFactoryBean;
    }

    /**
     * 配置CronTriggerFactoryBean
     *
     * @return CronTriggerFactoryBean
     */
    @Bean("productSellDailyTriggerFactoryBean")
    public CronTriggerFactoryBean createCronTriggerFactoryBean() {
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setName("product_sell_daily_trigger");
        triggerFactoryBean.setGroup("job_product_sell_daily_group");
        triggerFactoryBean.setJobDetail(jobDetailsFactory.getObject());
        //设定cron表达式 每天零点秒钟执行
        triggerFactoryBean.setCronExpression("0 0 0 * * ? ");
        return triggerFactoryBean;
    }

    /**
     * 创建调度工厂
     *
     * @return SchedulerFactoryBean
     */
    @Bean("schedulerFactory")
    public SchedulerFactoryBean createSchedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(productSellDailyTriggerFactoryBean.getObject());
        return schedulerFactoryBean;
    }


}
