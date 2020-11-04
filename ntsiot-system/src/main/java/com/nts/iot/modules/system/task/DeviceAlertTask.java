package com.nts.iot.modules.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.model.Device;
import com.nts.iot.modules.system.service.DeviceAlertService;
import com.nts.iot.modules.system.service.DeviceRealAlertService;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class DeviceAlertTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceAlertService deviceAlertService;

    @Autowired
    private DeviceRealAlertService deviceRealAlertService;

    /**
     * 超速报警
     */
    @Scheduled(cron = "0 */4 * * * ?")
    public void speedAlert() {
        log.info("超速报警 ....");
        // 获取mysql中所有没有激活的设备
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("del_flag", 1);
        deviceQueryWrapper.eq("enabled", 1);
        deviceQueryWrapper.eq("activation", 2);
        // 激活设备
        List<Device> deviceList = deviceService.list(deviceQueryWrapper);
        List<CollectMessage> messages = new ArrayList<>();
        // 当前时间 到小时 key 用于查询Redis
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd-HH");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 当前时间过去的4分钟
        long timeMillis = System.currentTimeMillis() - 1000 * 60 * 4;

        for (int i = 0; i < deviceList.size(); i++) {
            // 取出 Redis VECHILE_RECORD 中的值
            String data = redisUtil.getData(RedisKey.VECHILE_RECORD_TASK + date + ":" + deviceList.get(i).getDeviceNo());
            if (StringUtils.isNotBlank(data)) {
                //  json 转为list
                List<CollectMessage> collectMessages = JsonUtil.jsonConvertList(data, CollectMessage.class);
                if (!CollectionUtils.isEmpty(collectMessages)) {
                    for (int j = 0; j < collectMessages.size(); j++) {

                        long timeLong = 0L;
                        try {
                            // 将 collectMessages 中的 time 时间转为 时间戳 格式
                            timeLong = simpleDateFormat.parse(collectMessages.get(j).getTime()).getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (timeLong != 0 &&
                                timeMillis < timeLong &&
                                StringUtils.isNotBlank(collectMessages.get(j).getSpeed())
                                && deviceList.get(i).getSpeedAlert() != null
                                && deviceList.get(i).getSpeedAlert().compareTo(Double.parseDouble(collectMessages.get(j).getSpeed())) < 0) {
                            collectMessages.get(j).setAlertType("alert_type_002");
                            messages.add(collectMessages.get(j));

                            // 加入 device_real_alert 数据  数组最后一条数据
                            if (j == (collectMessages.size() - 1)) {
                                deviceRealAlertService.addDeviceRealAlert(collectMessages.get(j));
                            }
                        }
                        if (timeLong != 0 &&
                                timeMillis < timeLong &&
                                StringUtils.isNotBlank(collectMessages.get(j).getSpeed())
                                && deviceList.get(i).getSpeedAlert() != null
                                && deviceList.get(i).getSpeedAlert().compareTo(Double.parseDouble(collectMessages.get(j).getSpeed())) > 0
                                && j == (collectMessages.size() - 1)) {
                            // 加入 device_real_alert 数据  数组最后一条数据
                            deviceRealAlertService.delDeviceRealAlert(collectMessages.get(j));
                        }
                    }
                }
            }
        }
        deviceAlertService.create(messages, null);

    }

}
