package com.yzc.bigdata.competitor.config;

import com.yzc.bigdata.competitor.script.TimingUpdate;
import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Classname QuartzConfig
 * @Author lizonghuan
 * @Description quartz定时配置类
 * @Date-Time 2019/5/29-19:32
 * @Version 1.0
 */
@Configuration
public class QuartzConfig {

    //程序启动时间
    public static final Date startDate = new Date();


    @Bean
    public JobDetail teatQuartzDetail(){
        return JobBuilder.newJob(TimingUpdate.class).withIdentity("timingUpdate")
                .storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.
                simpleSchedule().withIntervalInSeconds(604800).repeatForever();
        CronTriggerImpl trigger = new CronTriggerImpl();

        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("timing")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
