/*******************************************************************************
 * @(#)RoadUtil.java 2019-05-14
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.*;
import io.netty.util.internal.StringUtil;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-14 19:12
 */
public class RoadUtil {

    /**
     * 根据采集点的信息获得采集点的路径
     *
     * @param messages 采集点信息
     * @return
     */
    public static List<Road> getRole(List<CollectMessage> messages) {
        List<Road> roads = new ArrayList<>();
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            CollectMessage c = JSONObject.parseObject(JsonUtil.getJson(o), CollectMessage.class);
            if (c.getLongitude().equals("0.0") || c.getLatitude().equals("0.0")) {
                it.remove();
            }
        }
        for (int i = 0; i < messages.size() - 1; i++) {
            Road road = new Road();
            // 起始点
            road.setFrom(messages.get(i));
            // 到达点
            road.setTo(messages.get(i + 1));
            // 开始时间
            road.setBeginTime(messages.get(i).getTime());
            // 到达时间
            road.setEndTime(messages.get(i + 1).getTime());
            // 设备编号
            road.setDeviceNo(messages.get(i).getDeviceNo());
            // 距离
            //double distince = GpsUtil.getDistanceMeter(road.getFrom(), road.getTo(), Ellipsoid.WGS84);
            double distince = GpsUtil.getDistance(road.getFrom(), road.getTo());

            //road.setDistance((double)Math.round(distince));
            road.setDistance(distince);
            road.setSpeed(messages.get(i).getSpeed());
            roads.add(road);
        }
        return roads;
    }

    public static double getDistance(List<Road> roads) {
        double sum = 0.0d;
        for (Road road : roads) {
            sum = sum + road.getDistance();
        }
        return sum;
    }


    public static RoadStatisitcInfo getRoadStatisticInfo(final List<Road> roads,
                                                         final List<Trajectory> trajectories,
                                                         final String deviceNo,
                                                         RedisUtil redisUtil) {

        RoadStatisitcInfo roadStatisitcInfo = new RoadStatisitcInfo();
        Double totalDistance = 0.0d;
        Long totalPauseTime = 0L;
        Long totalRunTime = 0L;
        Long totalPauseCount = 0L;
        Long total0verSpeedCount = 0L;
        Double totalDistanceFenceIn = 0.0d;
        Double totalDistanceFenceOut = 0.0d;
        Double avgSpeed = 0.0d;


        for (Road road : roads) {
            totalDistance = totalDistance + road.getDistance();
            if (0 == road.getDistance()) {
                totalPauseCount = totalPauseCount + 1;
            }
            Map<String, Long> map = getPauseTime(trajectories);
            totalPauseTime = totalPauseTime + (map.get("stopTime") != null ? map.get("runTime") : 0L);
            totalRunTime = totalRunTime + (map.get("runTime") != null ? map.get("runTime") : 0L);
            /*------------------- 计算平均速度： 总距离 / 运行时间---------------*/
            if (totalRunTime <= 0) {
                avgSpeed = 0.0d;
            } else {
                Long runTimeHour = totalRunTime / 60;// 分转换为小时
                if (runTimeHour != 0L) {
                    Double speed = totalDistance / runTimeHour;  // 米 / 小时
                    avgSpeed = Double.valueOf(String.format("%.2f", speed)); // 取小数点后两位
                }
            }
            /*--------------------计算超速告警---------------------------------*/
            String lockKey = RedisKey.TRACKER_KEY + deviceNo;
            String deviceInfo = redisUtil.getData(lockKey);
            if (!StringUtil.isNullOrEmpty(deviceInfo)) {
                DeviceVo device = JSON.parseObject(deviceInfo, DeviceVo.class);
                if (device.getSpeedAlert() != null) {
                    if (Double.parseDouble(road.getSpeed()) > device.getSpeedAlert()) {
                        total0verSpeedCount = total0verSpeedCount + 1;
                        String rKey = RedisKey.OVERSPEED_ALARM_KEY + deviceNo;
                        OverSpeedAlarmVo vo = new OverSpeedAlarmVo();
                        vo.setSpeed(road.getSpeed());
                        vo.setAlertSpeed(device.getSpeedAlert());
                        vo.setTrackTime(road.getBeginTime());
                        vo.setLongitude((Double.parseDouble(road.getTo().getLongitude()) +
                                Double.parseDouble(road.getFrom().getLongitude())) / 2);
                        vo.setLatitude((Double.parseDouble(road.getTo().getLatitude()) +
                                Double.parseDouble(road.getFrom().getLatitude())) / 2);

                        String location = "";
                        try {
                            location = new BaiDuGeocoderUtils().getBaiDuLocation("zh-cn", vo.getLatitude() + "", vo.getLongitude() + "", null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        vo.setAddress(location);
                        if (null != redisUtil.getData(rKey)) {
                            List<OverSpeedAlarmVo> overSpeedAlarmList = JsonUtil.jsonConvertList(redisUtil.getData(rKey), OverSpeedAlarmVo.class);
                            overSpeedAlarmList.add(vo);
                            redisUtil.addRedis(rKey, JsonUtil.getJson(overSpeedAlarmList));

                        } else {
                            List<OverSpeedAlarmVo> overSpeedAlarmList = new ArrayList<OverSpeedAlarmVo>();
                            overSpeedAlarmList.add(vo);
                            redisUtil.addRedis(rKey, JsonUtil.getJson(overSpeedAlarmList));
                        }

                    }
                }

                /*--------------------当前设备如果绑定过围栏，则计算围栏距离-------------------------------------*/
                if (device.getFenceId() != null) {
                    String fenceKey = RedisKey.RESTRICTIONS_FENCE + device.getFenceId();
                    String fenceInfo = redisUtil.getData(fenceKey);
                    if (StringUtil.isNullOrEmpty(fenceInfo)) {
                        Fence fence = JSON.parseObject(fenceInfo, Fence.class);
                        CollectMessage from = road.getFrom();
                        CollectMessage to = road.getTo();
                        List<String> coordinates = new ArrayList<String>();
                        coordinates.add(fence.getCoordinate());
                        if (fence.getStatus().equals("fence_status_001")) {
                            if (EnclosureUtil.checkRail(coordinates, from)
                                    && EnclosureUtil.checkRail(coordinates, from)) {//要起点和终点都在范围内
                                totalDistanceFenceOut = totalDistanceFenceOut + road.getDistance();
                            }
                        }
                        ;
                        if (fence.getStatus().equals("fence_status_002")) {
                            if (EnclosureUtil.checkRail(coordinates, from)
                                    && EnclosureUtil.checkRail(coordinates, from)) {//要起点和终点都不在范围内
                                totalDistanceFenceIn = totalDistanceFenceIn + road.getDistance();
                            }
                        }
                    }
                }

            }
        }
        roadStatisitcInfo.setTotalDistance(totalDistance);
        roadStatisitcInfo.setAvgSpeed(avgSpeed);
        roadStatisitcInfo.setTotal0verSpeedCount(total0verSpeedCount);
        roadStatisitcInfo.setTotalPauseCount(totalPauseCount);
        roadStatisitcInfo.setTotalPauseTime(totalPauseTime);
        roadStatisitcInfo.setTotalRunTime(totalRunTime);
        roadStatisitcInfo.setTotalDistanceFenceIn(totalDistanceFenceIn);
        roadStatisitcInfo.setTotalDistanceFenceOut(totalDistanceFenceOut);

        return roadStatisitcInfo;
    }


    /**
     * 算出停留时间
     *
     * @param trajectories
     * @return
     */
    public static Map<String, Long> getPauseTime(List<Trajectory> trajectories) {
        Map<String, Long> timeMap = new HashMap<>();
        // 单位·毫秒
        Long stopTime = 0L;
        Long runTime = 0L;
        Collections.sort(trajectories);
        if (trajectories.size() > 1) {
            for (int i = 0; i < trajectories.size() - 1; i++) {
                Trajectory before = trajectories.get(i);
                Trajectory after = trajectories.get(i + 1);
                Long timeCha = (DateUtil.parse(after.getTime(), "yyyy-MM-DD HH:mm:ss").getTime() - DateUtil.parse(before.getTime(), "yyyy-MM-DD HH:mm:ss").getTime());
                // 如果坐标点相同，则算停留点
                if (after.getLatitude().equals(before.getLatitude()) && after.getLongitude().equals(before.getLongitude())) {
                    stopTime += timeCha;
                } else {
                    runTime += timeCha;
                }
            }
        }
        // 停留时间
        timeMap.put("stopTime", stopTime);
        // 运行时间
        timeMap.put("runTime", runTime);
        return timeMap;
    }

    public static List<Trajectory> getTrajectory(List<CollectMessage> messages) {
        List<Trajectory> trajectories = new ArrayList<>();
        for (CollectMessage message : messages) {
            Trajectory trajectory = initTrajectory(message);
            trajectories.add(trajectory);
        }
        return trajectories;
    }

    public static Trajectory initTrajectory(CollectMessage message) {
        Trajectory trajectory = new Trajectory();
        trajectory.setLatitude(message.getLatitude());
        trajectory.setLongitude(message.getLongitude());
        trajectory.setBeamState(message.getBeamState());
        trajectory.setCellPower(message.getCellPower());
        trajectory.setCellVoltage(message.getCellVoltage());
        trajectory.setCourse(message.getCourse());
        trajectory.setOutCellPower(message.getOutCellPower());
        trajectory.setOutCellVoltage(message.getOutCellVoltage());
        trajectory.setPoiupState(message.getPoiupState());
        trajectory.setSatellites(message.getSatellites());
        trajectory.setSimSignal(message.getSimSignal());
        trajectory.setSpeed(message.getSpeed());
        trajectory.setTime(message.getTime());
        trajectory.setSystemTime(message.getSystemTime());
        return trajectory;
    }
}
