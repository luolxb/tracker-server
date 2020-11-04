package com.nts.iot.modules.miniApp.quartztask;

import com.nts.iot.modules.miniApp.model.AppointmentManager;
import com.nts.iot.modules.miniApp.service.AppointmentManagerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: hamsun
 * @Description: 定时短信发送任务
 * @Date: 2019/10/27 14:09
 */
@Slf4j
@Component
public class SMSMthTask extends QuartzJobBean {

    @Autowired
    private AppointmentManagerService appointmentManagerService;

    @Autowired
    private SMSService smsService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        List<AppointmentManager> appointmentManagers = appointmentManagerService.queryByPeriod(2);
        smsService.sendSMSBatch(appointmentManagers);
        log.info("------定时任务成功 SMSTask: ------" + new Date());
    }

}
