package com.nts.iot.modules.system.task;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.constant.MiniAppConstants;
import com.nts.iot.modules.system.model.RealTask;
import com.nts.iot.modules.system.service.RealTaskService;
import com.nts.iot.modules.system.service.TaskTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
//@JobHandler
@EnableScheduling
public class MissionCheckTask {

    @Autowired
    private TaskTemplateService taskTemplateService;

    @Autowired
    private RealTaskService realTaskService;

    // 每10分钟执行一次
    @Scheduled(cron = "0 */10 * * * ?")
    public void execute() {
//        System.out.println(new Date());
        /* TODO 任务判断是否完成相关task （放在小程序后完成）*/
        List<RealTask> tasks = realTaskService.getTask();
        if (tasks != null && tasks.size() > 0) {
            for (RealTask task : tasks) {
                // 系统任务结束时间
                long sysPatrolEndTime = DateUtil.parse(task.getSysPatrolEndTime()).getTime();
                // 实际任务结束时间 大于 系统任务结束时间 则认为超时
                if (System.currentTimeMillis() > sysPatrolEndTime) {
                    // 超时
                    task.setStatus(MiniAppConstants.MISSION_TIMEOUT);
                    realTaskService.changeStatus(task);
                }
            }
        }
    }

    private String getStartTimeStr(){
        LocalTime localTime = LocalTime.now();
        String hhmmss = localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond();
        System.out.println("当前时间：" +hhmmss);
        return hhmmss;
    }

    public static void main(String[] args) {
        DateUtil.parse("2019-07-01 1:1:1");
    }
}
