package com.nts.iot.modules.miniApp.quartztask;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: hamsun
 * @Description: 任务配置
 * @Date: 2019/10/27 14:12
 */
@Configuration
public class TaskConfig {
    // 定义要执行的Task任务类
    @Bean
    public JobDetail SMSDayJobDetail() {
        return JobBuilder.newJob(SMSDayTask.class).withIdentity("SMSDayJob").storeDurably().build();
    }

    @Bean
    public JobDetail SMSWeekJobDetail() {
        return JobBuilder.newJob(SMSWeekTask.class).withIdentity("SMSWeekJob").storeDurably().build();
    }

    @Bean
    public JobDetail SMSMthJobDetail() {
        return JobBuilder.newJob(SMSDayTask.class).withIdentity("SMSMthJob").storeDurably().build();
    }


//    @Bean
//    public SimpleTrigger emailJobDetailTrigger() {
//        // Simple类型：可设置时间间隔、是否重复、触发频率（misfire机制）等
//        // 这里我设置的10s的定时任务
//        SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(10).repeatForever();
//
//        // 一个Trigger只对应一个Job，Schedule调度器调度Trigger执行对应的Job
//        SimpleTrigger sTrigger = TriggerBuilder.newTrigger().forJob(SMSDayJobDetail()).
//                withIdentity("sampleTrigger").withDescription("simple类型的触发器").withSchedule(ssb).build();
//        return sTrigger;
//    }

    // Cron触发器定义与设置
    @Bean
    public CronTrigger SMSTaskDayTrigger() {
        // Cron类型：通过cron表达式设置触发规则
        CronScheduleBuilder csbDay = CronScheduleBuilder.cronSchedule(String.format("0 0 17 * * ? *"))
                .withMisfireHandlingInstructionDoNothing();//每天17点执行一次
        // 一个Trigger只对应一个Job，Schedule调度器调度Trigger执行对应的Job
        CronTrigger cTrigger = TriggerBuilder.newTrigger().forJob(SMSDayJobDetail()).
                withIdentity("dayCronTrigger").withDescription("corn类型的触发器").withSchedule(csbDay).startNow().build();
        return cTrigger;
    }

    @Bean
    public CronTrigger SMSTaskWeekTrigger() {
        // Cron类型：通过cron表达式设置触发规则
        CronScheduleBuilder csbWeek = CronScheduleBuilder.cronSchedule(String.format("0 0 17 ? * FRI"))
                .withMisfireHandlingInstructionDoNothing();//每周五17点执行一次

        CronTrigger cTrigger = TriggerBuilder.newTrigger().forJob(SMSWeekJobDetail()).
                withIdentity("weekCronTrigger").withDescription("corn类型的触发器").withSchedule(csbWeek).startNow().build();
        return cTrigger;
    }

    @Bean
    public CronTrigger SMSTaskMthTrigger() {
        // Cron类型：通过cron表达式设置触发规则
        CronScheduleBuilder csbMonth = CronScheduleBuilder.cronSchedule(String.format("0 0 17 l * ? *"))
                .withMisfireHandlingInstructionDoNothing();//每月最后一天17点执行一次
        CronTrigger cTrigger = TriggerBuilder.newTrigger().forJob(SMSMthJobDetail()).
                withIdentity("mthCronTrigger").withDescription("corn类型的触发器").withSchedule(csbMonth).startNow().build();
        return cTrigger;
    }
}
