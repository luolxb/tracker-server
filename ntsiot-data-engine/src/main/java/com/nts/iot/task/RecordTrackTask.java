/*******************************************************************************
 * @(#)RecordTrackTask.java 2019-05-02
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.task;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.*;
import com.nts.iot.entity.Record;
import com.nts.iot.service.RecordService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import com.nts.iot.util.RoadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * <p>
 * 一个小时执行一次，年月日时+设备编号为key，将轨迹信息存储到es中。
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-02 22:21
 */
@Slf4j
@Component
//@JobHandler
@EnableScheduling
public class RecordTrackTask {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RecordService recordService;

    /**
     * 每小时执行一次的任务、每次执行的任务的时间是每小时的15分钟。
     *
     * @throws Exception
     */
    // 每小时执行一次的任务
    @Scheduled(cron = "0 15 * * * ?")
    //@Scheduled(cron = "0 16 15 ? * *")
    // 每30秒执行一次
//    @Scheduled(cron = "30 * * * * ?")
    public void execute() {

        Calendar calendar = Calendar.getInstance();

        String key;

        //获得当前的小时
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);

        // 当前是0点，也就意味着应该查看上一天23点的时段
        if (hour == 0) {
            key = DateUtil.yesterday().toString("yyyy-MM-dd") + "-23";
        } else {
            Integer date = calendar.get(Calendar.HOUR_OF_DAY) - 1;
            String dateStr;
            if (date < 10) {
                dateStr = "0" + date;
            } else {
                dateStr = String.valueOf(date);
            }
            key = DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + "-" + dateStr;
        }
        //System.out.println("将轨迹信息存储到es中key=" + key);
        log.info("将轨迹信息存储到es中key=" + key);

        // 车锁list
        List<String> lockList = JsonUtil.jsonConvertList(redisUtil.getData(RedisKey.TRACKER_LIST_KEY), String.class);
        // 根据key查询出来缓存中的记录
        List<String> keys = new ArrayList<>();
        for (String lockCode : lockList) {
            String redisKey = RedisKey.TRACKER_DEVICE_RECORD_TASK + key + ":" + lockCode;
            keys.add(redisKey);
        }
        //List<String> keys = redisUtil.fuzzySearchRedis(RedisKey.TRACKER_DEVICE_RECORD_TASK + key + ":" + "*");
        if (keys != null && keys.size() > 0) {
            List<Record> records = new ArrayList<>();
            for (String item : keys) {  // 查询出来的key，拆分出来设备编号、时间
                List<CollectMessage> messages = JsonUtil.jsonConvertList(redisUtil.getData(item), CollectMessage.class);
                if (messages == null) {   // 获得缓存中的上传的采集点
                    continue;
                }
                String deviceNo = item.split(":")[3];  // 获得智能锁的编号
                List<Road> roads = RoadUtil.getRole(messages); // 遍历道路
                List<Trajectory> trajectories = RoadUtil.getTrajectory(messages);

                Record record = new Record();
                record.setId(key + "." + deviceNo);  // 定位设备的编码

                record.setDeviceNo(deviceNo);
                record.setRoads(roads);
                record.setTrajectories(trajectories);

                RoadStatisitcInfo info = RoadUtil.getRoadStatisticInfo(roads, trajectories, deviceNo, redisUtil);

                record.setTotalDistance(info.getTotalDistance());
                record.setTotal0verSpeedCount(info.getTotal0verSpeedCount());
                record.setTotalPauseCount(info.getTotalPauseCount());
                record.setTotalDistanceFenceOut(info.getTotalDistanceFenceOut());
                record.setTotalDistanceFenceIn(info.getTotalDistanceFenceIn());
                record.setTotalPauseTime(info.getTotalPauseTime());
                record.setTotalRunTime(info.getTotalRunTime());

                // 插入记录
                records.add(record);
                // 删除轨迹缓存
                redisUtil.deleteByKey(item);
            }
            if (records.size() > 0) {
                recordService.saveAll(records);
                log.info("将轨迹信息存储到es中key=" + key + ",保存信息：" + JsonUtil.getJson(records));
            }
        }

        //System.out.println("每一小时执行一次任务！time=" + System.currentTimeMillis());
    }


}
