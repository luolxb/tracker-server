package com.nts.iot.modules.system.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.modules.system.model.Device;
import com.nts.iot.modules.system.service.DeviceService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
//@JobHandler
@EnableScheduling
public class DeviceLocationTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DeviceService deviceService;

    @Value("${track.proxy.url}")
    private String trackProxyUrl;

    /**
     * 每2分钟获取一次定位信息，并修改device表
     */
    @Scheduled(cron = " 0 */2 * * * ?")
    public void trackLocationTask() {
        log.info("每2分钟获取一次定位信息，并修改device表");
        // 获取mysql中所有没有激活的设备
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("del_flag", 1);
        deviceQueryWrapper.eq("enabled", 1);
        deviceQueryWrapper.eq("activation", 2);
        List<Device> deviceList = deviceService.list(deviceQueryWrapper);
        deviceList
                .stream()
                .filter(device -> StringUtils.isNotBlank(device.getDeviceNo()))
                .forEach(device -> {
                    Device device1 = new Device();
                    device1.setDeviceNo(device.getDeviceNo());
                    // 需要记录最后登陆时间，静止时间，用于统计离线时长和静止时长
                    // 如果在缓存中可以查到心跳数据 就为在线否则离线
                    boolean data = redisUtil.hasKey(RedisKey.TRACKER_HEARTBEAT_KEY + device.getDeviceNo());
                    if (data) {
                        // 获取到缓存中的数据
                        CollectMessage message = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.VECHILE_STATE + device.getDeviceNo()), CollectMessage.class);
                        if (null != message) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (StringUtils.isBlank(message.getSpeed())
                                    || message.getSpeed().equals("0.0")) {
                                // 静止
                                device1.setStatus(3);
                                // 如果设备上线只有心跳 尤其是第一次 静止时间为空
                                if (null == device.getRestTime()) {
                                    device1.setRestTime(new Date());
                                }
                                try {
                                    device1.setGpsDate(simpleDateFormat.parse(message.getTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                device1.setLastOnLineTime(new Date());
                            } else {
                                // 在线
                                device1.setStatus(2);
                                try {
                                    device1.setGpsDate(simpleDateFormat.parse(message.getTime()));
                                    device1.setRestTime(simpleDateFormat.parse(message.getTime()));
                                    device1.setLastOnLineTime(simpleDateFormat.parse(message.getTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            String latitude = message.getLatitude();
                            String longitude = message.getLongitude();
                            device1.setLocation(longitude + "," + latitude);

                        }
                    } else {
                        // 离线
                        device1.setStatus(1);
                        device1.setRestTime(new Date());
                    }
                    deviceService.updateDeviceLocation(device1);
                });
    }
}
