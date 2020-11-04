package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.RidingTrack;
import com.nts.iot.modules.miniApp.model.HistoryApiDto;
import com.nts.iot.modules.system.model.Bike;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


/**
 * @Author zhc@rnstec.com
 * @Description 车辆管理
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */
public interface BikeManagerService extends IService<Bike> {

    /**
     * 查询列表
     * @param pageable
     * @param bike
     * @param jurisdictions
     * @return
     */
    Map queryAll(Pageable pageable, Bike bike, List<String> jurisdictions);


    /**
     * 查询列表App
     * @param pageable
     * @param bike
     * @param jurisdictions
     * @return
     */
    List<Bike> queryAllApp(List<String> jurisdictions);

    /**
     * 获取车辆状态
     * @param bikeBarcode
     * @param maOpenId
     * @return
     */
    Long getBikeStatus(String bikeBarcode, String maOpenId);

    /**
     * 车辆列表（redis初始化）
     * @return
     */
    void initBikes();

    /**
     * 查询历史轨迹
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    RidingTrack historicalTrack(String bikeBarcode, Long startTime, Long endTime);


    /**
     * 根据bikeBarcode查询 Bike
     * @param bikeBarCode
     * @return
     */
    Bike getBikeByBarcode(String bikeBarCode);

    // 根据bikeBarcode查询 Bike
    Bike getBikeByDeviceNo(String deviceNo);


    /**
     * 根据车编号判断是否可以关锁
     * @param lockBarCode
     * @return
     */
    boolean checkCloseLock(String lockBarCode);

    /**
     * 根据车编号判断是否可以开锁
     * @param lockBarCode
     * @return
     */
    boolean checkOpenLock(String lockBarCode);

    Map<String, Object> getBikeStatus(Bike bike, String openId);

    /**
     * 查询历史轨迹,对外api专用
     * @param bikeBarcode
     * @param startTime
     * @param endTime
     * @return
     */
    HistoryApiDto historicalTrackApi(String bikeBarcode, Long startTime, Long endTime);
}
