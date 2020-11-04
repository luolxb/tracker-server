package com.nts.iot.modules.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.modules.system.model.Device;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class DeviceActivationTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeviceService deviceService;

    /**
     * 每小时执行
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void activationTask() {
        // 获取mysql中所有没有激活的设备
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("del_flag", 1);
        deviceQueryWrapper.eq("enabled", 1);
        deviceQueryWrapper.eq("activation", 1);
        List<Device> deviceList = deviceService.list(deviceQueryWrapper);

        deviceList.forEach(device -> {
            String redisKey = RedisKey.VECHILE_STATE + device.getDeviceNo();
            CollectMessage message = JsonUtil.jsonConvertObject(redisUtil.getData(redisKey), CollectMessage.class);
            // 查看缓存中设备有没有数据
            if (null != message) {
                // 如果有数据激活设备，设备在线
                Device device1 = new Device();
                device1.setActivation(2);
                device1.setActivationTime(new Date());
                device1.setLastOnLineTime(new Date());
                device1.setRestTime(new Date());
                device1.setStatus(2);
                device1.setId(device.getId());
                try {
                    boolean update = deviceService.updateById(device1);
                    if (!update) {
                        log.error("激活失败，{}", device.getDeviceNo());
                    }
                } catch (Exception e) {
                    log.error("激活失败：", e);
                }
            }
        });
    }
}
