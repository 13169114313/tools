package com.goldmsg.gmvcs.syn.core.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Configuration
public class QuartzConfig {
    @Autowired
    private SpringJobFactory springJobFactory;
    @Autowired
    ApplicationArguments args;

    @Bean(name = "MySchedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 加载quartz数据源配置
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setJobFactory(springJobFactory);
            Scheduler Scheduler = schedulerFactoryBean.getScheduler();
//            Scheduler.getListenerManager().addJobListener(new MyJobListener());

        return schedulerFactoryBean;
    }

    @Bean(name = "MyScheduler")
    public Scheduler scheduler() throws IOException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        return scheduler;
    }

    /**
     * 加载quartz数据源配置
     *
     * @return
     * @throws IOException
     */
    @Bean
    @Autowired
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        String tConfigPath;
        List<String> argList = args.getNonOptionArgs();
        Resource location = null;
        if ((1 <= argList.size()) && (argList.get(0).equals("debug"))){
            location = new ClassPathResource("/quartz.properties");
        }else if (1 <= argList.size()) {
            tConfigPath = "file:///" + argList.get(0) + "/quartz.properties";
            ResourceLoader loader = new DefaultResourceLoader();
            location = loader.getResource(tConfigPath);
        }

        propertiesFactoryBean.setLocation(location);
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
