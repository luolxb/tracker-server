package com.nts.iot.rest;

import com.nts.iot.dto.DeviceStateQueryDTO;
import com.nts.iot.dto.DeviceStatisticDto;
import com.nts.iot.dto.TrajectoryDto;
import com.nts.iot.service.FindTrajectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询轨迹：
 * 开始时间戳，和结束时间戳，车锁编号 分别到redis缓存中和Elasticsearch查询记录
 */
@RestController
@RequestMapping("")
public class FindTrajectoryController {

    @Autowired
    private FindTrajectoryService findRecordService;

    /**
     * 查询轨迹
     * 开始时间戳，和结束时间戳，车锁编号 分别到
     * redis缓存中和
     * Elasticsearch查询记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param lockNo    车锁编号
     */
    @GetMapping("/findRecord")
    public List<TrajectoryDto> findRecord(@RequestParam("startTime") Long startTime, @RequestParam("endTime") Long endTime, @RequestParam("lockNo") String lockNo) {
        return findRecordService.findTrajectoryDto(startTime, endTime, lockNo);
    }

    /**
     * 查询轨迹
     * 开始时间戳，和结束时间戳，车锁编号 分别到
     * redis缓存中和
     * Elasticsearch查询记录
     *
     * queryParam
     */
    @PostMapping("/findRecord/list")
    public List<TrajectoryDto> findRecordList(@RequestBody DeviceStateQueryDTO queryParam) {
        return findRecordService.findRecordList(queryParam);
    }


    /**
     * 查询统计信息
     *
     * @param queryParam (开始时间戳，和结束时间戳，车锁编号list)
     */
//    @PostMapping("/findBikeStaticList")
//    public List<BikeStaticDto> findBikeStaticList(@RequestBody DeviceStateQueryDTO queryParam) {
//        return findRecordService.findBikeStaticList(queryParam.getStartTime(), queryParam.getEndTime(), queryParam.getIds());
//    }

    /**
     * 查询统计信息（查询的统计的时间范围，不能超过30天，超过则直接提示---查询不能超过30天）
     *
     * @param queryParam * @param startTime 开始时间
     *                   * @param endTime   结束时间
     *                   * @param lockNo    车锁编号
     * @return List<DeviceStatisticDto>
     */
    @PostMapping("/findDeviceStatistic")
    public List<DeviceStatisticDto> findDeviceStatisticList(@RequestBody DeviceStateQueryDTO queryParam) {
        return findRecordService.findDeviceStatisticDtoList(queryParam.getStartTime(), queryParam.getEndTime(), queryParam.getDeviceNo());
    }

    /**
     * 通过锁list 查询轨迹信息
     * @param startTime
     * @param endTime
     * @param bikeNo
     * @return
     */
//    @GetMapping("/findRecordByLockNum")
//    public List<BikeTrajectoryDto> findRecordByBikeNum(@RequestParam("startTime") Long startTime, @RequestParam("endTime") Long endTime, @RequestParam("bikeNo") String bikeNo) {
//        return findRecordService.findRecordByBikeNo(startTime, endTime, bikeNo);
//    }


}
