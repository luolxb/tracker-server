package com.nts.iot.service;

import com.nts.iot.dto.*;

import java.util.List;

public interface FindTrajectoryService {
    /**
     * 查询记录
     *
     * @param startTime
     * @param lockNo
     * @return
     */
    List<TrajectoryDto> findTrajectoryDto(Long startTime, Long endTime, String lockNo);

    //DeviceStatisticDto findDeviceStatisticDto(Long startTime, Long endTime, String lockNo);

    List<DeviceStatisticDto> findDeviceStatisticDtoList(Long startTime, Long endTime, List<String> lockNoList);

    List<TrajectoryDto> findRecordList(DeviceStateQueryDTO queryParam);

    //BikeStaticDto findBikeStaticDto(Long startTime, Long endTime, String lockNo);

    //List<BikeStaticDto> findBikeStaticList(Long startTime, Long endTime, List<String> lockNoList);

    //List<BikeTrajectoryDto> findRecordByBikeNo(Long startTime, Long endTime, String bikeNo);
}
