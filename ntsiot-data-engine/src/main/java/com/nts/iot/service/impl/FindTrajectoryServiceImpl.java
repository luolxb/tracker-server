package com.nts.iot.service.impl;

import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.*;
import com.nts.iot.entity.Record;
import com.nts.iot.repository.RecordRepository;
import com.nts.iot.service.FindTrajectoryService;
import com.nts.iot.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindTrajectoryServiceImpl implements FindTrajectoryService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<TrajectoryDto> findTrajectoryDto(Long startTime, Long endTime, String lockNo) {
        //先把查询时间分片
        List<String> dateList = FormatTime.formatTime(startTime, endTime);
        // esKey
        List<String> esKeyList = new ArrayList<>();
        List<TrajectoryDto> trajectoryDtoList = new ArrayList<TrajectoryDto>();
        // 1.先去redis中查找
        findTrajectoryDtoFromRedis(dateList, lockNo, trajectoryDtoList, esKeyList);
        // 2.说明 redis中没有值，然后再去es中去查询
        if (esKeyList.size() > 0) {
            List<Record> recordList = getRecordListFromEs(esKeyList);
            if (recordList.size() > 0) {
                for (Record record : recordList) {
                    List<Trajectory> trajectoryList = record.getTrajectories();
                    if (trajectoryList != null && trajectoryList.size() > 0) {
                        for (Trajectory trajectory : trajectoryList) {
                            trajectoryDtoList.add(getTrajectoryDto(trajectory.getLongitude(), trajectory.getLatitude(), trajectory.getTime(), trajectory.getSpeed(), trajectory.getCourse(),trajectory.getSimSignal()));
                        }
                    }
                }
            }
        }

        // 排序
        Collections.sort(trajectoryDtoList);

        List<TrajectoryDto> resultList = FilterCoordinatesUtil.filterTrajectory(trajectoryDtoList);

        // 返回过滤后的结果
        return filtration(startTime, endTime, resultList,lockNo);
    }


    @Override
    public List<DeviceStatisticDto> findDeviceStatisticDtoList(Long startTime, Long endTime, List<String> deviceNoList) {

        List<DeviceStatisticDto> deviceStatisticDtolist = new ArrayList<>();
        List<String> dateList = FormatTime.formatTime(startTime, endTime);
        List<String> esKeyList = new ArrayList<>();

        for (String deviceNo : deviceNoList) {
            DeviceStatisticDto deviceStatisticDto = findDeviceStaticDtoFromEs(dateList, deviceNo, esKeyList);
            deviceStatisticDtolist.add(deviceStatisticDto);
        }
        return deviceStatisticDtolist;
    }

    @Override
    public List<TrajectoryDto> findRecordList(DeviceStateQueryDTO queryParam) {
        List<TrajectoryDto> trajectoryDtoList = new ArrayList<>();
        queryParam.getDeviceNo().forEach( deviceNo -> {
            List<TrajectoryDto> trajectoryDto = this.findTrajectoryDto(queryParam.getStartTime(), queryParam.getEndTime(), deviceNo);
            trajectoryDtoList.addAll(trajectoryDto);
        });
        return trajectoryDtoList;
    }


    //按时间分片查找轨迹记录
    private String findRecordByTimeTick(final String timeTick, final String deviceNo) {
        String key = RedisKey.TRACKER_DEVICE_RECORD_TASK + timeTick + ":" + deviceNo;
        // 先查redis看是否有数据
        return redisUtil.getData(key);
    }

    //先从Redis查找轨迹记录
    private void findTrajectoryDtoFromRedis(final List<String> dateList,
                                            final String deviceNo,
                                            List<TrajectoryDto> trajectoryDtoList,
                                            List<String> esKeyList) {
        for (String s : dateList) {
            String collectMessage = findRecordByTimeTick(s, deviceNo);
            // 判断是否为空，如果为空则去 Elasticsearch中查询
            if (collectMessage != null && !"".equals(collectMessage)) {
                // 查询出轨迹记录List
                List<CollectMessage> collectMessagelist = JsonUtil.jsonConvertList(collectMessage, CollectMessage.class);
                if (collectMessagelist != null && collectMessagelist.size() > 0) {
                    for (CollectMessage message : collectMessagelist) {
                        trajectoryDtoList.add(getTrajectoryDto(message.getLongitude(), message.getLatitude(), message.getTime(), message.getSpeed(), message.getCourse(),message.getSimSignal()));
                    }
                }
            } else {
                String esKey = s + "." + deviceNo;
                esKeyList.add(esKey);
            }
        }

    }

    private DeviceStatisticDto findDeviceStaticDtoFromEs(final List<String> dateList,
                                           final String deviceNo,
                                           List<String> esKeyList) {
        DeviceStatisticDto deviceStatisticDto = new DeviceStatisticDto();

        //总距离
        Double totalDistance = 0.0d;
        //总停留次数
        Long totalPauseCount = 0L;
        //超速次数
        Long totaloverspeedCount = 0L;
        //围栏里的距离
        Double totalDistanceInFence = 0.0d;
        //围栏外的距离
        Double totalDistanceOutFence = 0.0d;
        // 平均骑行速度
        Long avgSpeed = 0L;
        Long totalRunTime = 0L;
        Long totalPauseTime = 0L;
        // 从es中获得定位设备信息
        List<Record> recordList = getRecordListFromEs(makeEsKeyList(dateList, deviceNo));
        if (recordList.size() > 0) {
            for (Record record : recordList) {
                totalDistance = totalDistance + (record.getTotalDistance() == null ? 0 : record.getTotalDistance());
                totaloverspeedCount = totaloverspeedCount + (record.getTotal0verSpeedCount() == null ? 0 : record.getTotal0verSpeedCount());
                totalPauseCount = totalPauseCount + (record.getTotalPauseCount() == null ? 0 :record.getTotalPauseCount());
                totalDistanceInFence = totalDistanceInFence + (record.getTotalDistanceFenceIn() == null ? 0.0d : record.getTotalDistanceFenceIn());
                totalDistanceOutFence = totalDistanceOutFence + (record.getTotalDistanceFenceOut() == null ? 0.0d : record.getTotalDistanceFenceOut());
                Map<String, Long> map = RoadUtil.getPauseTime(record.getTrajectories());
                totalPauseTime = totalPauseTime + (map.get("stopTime") != null ? map.get("runTime") : 0L);
                totalRunTime = totalRunTime + (map.get("runTime") != null ? map.get("runTime") : 0L);
            }
        }
        // 车辆编号
        deviceStatisticDto.setDeviceNo(deviceNo);
        deviceStatisticDto.setTotalDistance((double)Math.round((totalDistance / 1000) * 100) / 100);
        deviceStatisticDto.setTotalPauseCount(totalPauseCount);
        deviceStatisticDto.setTotalOverSpeedCount(totaloverspeedCount);
        deviceStatisticDto.setTotalDistanceInFence((double) Math.round((totalDistanceInFence / 1000) * 100) / 100);
        deviceStatisticDto.setTotalDistanceOutFence((double)Math.round((totalDistanceOutFence / 1000) * 100) / 100);
        totalPauseTime = Long.valueOf(String.valueOf(totalPauseTime / 1000 / 60));
        deviceStatisticDto.setTotalPauseTime(totalPauseTime);
        totalRunTime = Long.valueOf(String.valueOf(totalRunTime / 1000 / 60));
        deviceStatisticDto.setTotalRunTime(totalRunTime);
        deviceStatisticDto.setBeginTime(dateList.get(0));
        deviceStatisticDto.setEndTime(dateList.get(dateList.size() - 1));

        // 平均速度： 总距离 / 运行时间
        if (totalRunTime <= 0) {
            deviceStatisticDto.setAverageSpeed(0d);
        } else {
            // 分转换为小时
            Double runTimeHour = Double.valueOf(String.valueOf(totalRunTime)) / 60;
            // 千米 / 小时
            Double speed = deviceStatisticDto.getTotalDistance() / runTimeHour;
            // 取小数点后两位
            deviceStatisticDto.setAverageSpeed((double) Math.round(speed * 100) / 100);
        }
        return deviceStatisticDto;
    }


    private List<String> makeEsKeyList(List<String> dateList, String lockNo) {
        List<String> esKeyList = new ArrayList<>();
        for (String s : dateList) {
            // 添加 eskey
            String esKey = s + "." + lockNo;
            esKeyList.add(esKey);
        }
        return esKeyList;
    }

    private List<Record> getRecordListFromEs(List<String> esKeyList) {
        List<Record> recordList = new ArrayList<>();
        for (String s : esKeyList) {
            Optional<Record> record = recordRepository.findById(s);
            record.ifPresent(recordList::add);
        }
        return recordList;
    }


    private BikeTrajectoryDto getBikeTrajectoryDto(Long startTime, Long endTime, Bike bike) {
        List<TrajectoryDto> trajectoryDtoList = findTrajectoryDto(startTime, endTime, bike.getLockBarcode());
        BikeTrajectoryDto bikeTrajectoryDto = new BikeTrajectoryDto();
        bikeTrajectoryDto.setBikeNo(bike.getBikeBarcode());
        bikeTrajectoryDto.setLockNo(bike.getLockBarcode());
        bikeTrajectoryDto.setTrajectoryDtoList(trajectoryDtoList);
        return bikeTrajectoryDto;
    }

    /**
     * 获得TrajectoryDto
     *
     * @param longitude
     * @param latitude
     * @param time
     * @return
     */
    private TrajectoryDto getTrajectoryDto(String longitude, String latitude, String time, String speed, String course,String simSignal) {
        TrajectoryDto trajectoryDto = new TrajectoryDto();
        trajectoryDto.setLongitude(longitude);
        trajectoryDto.setLatitude(latitude);
        trajectoryDto.setTime(time);
        trajectoryDto.setMis(FormatTime.dateToTime(time));
        trajectoryDto.setSpeed(speed);
        trajectoryDto.setCourse(course);
        trajectoryDto.setSimSignal(simSignal);
        return trajectoryDto;
    }

    /**
     * 过滤
     *
     * @param trajectoryDtoList
     * @return
     */
    private List<TrajectoryDto> filtration(Long startTime, Long endTime, List<TrajectoryDto> trajectoryDtoList,String lockNo) {
        // 返回结果
        List<TrajectoryDto> newTrajectoryDtoList = new ArrayList<>();
        for (TrajectoryDto trajectoryDto : trajectoryDtoList) {
            Long mis = trajectoryDto.getMis();
            trajectoryDto.setDeviceNo(lockNo);
            /* 大于等于开始时间，小于等于结束时间 */
            if (startTime <= mis && mis <= endTime) {
                newTrajectoryDtoList.add(trajectoryDto);
            }
        }
        return newTrajectoryDtoList;
    }


    public static void main(String[] args) {
        double dev = 3.9999;
        dev = (double)Math.round(dev * 100) /100;
        System.out.println(dev);
    }
}
