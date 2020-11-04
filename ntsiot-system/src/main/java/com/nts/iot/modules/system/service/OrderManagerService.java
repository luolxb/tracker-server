package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.RidingTrack;
import com.nts.iot.modules.miniApp.model.RankByBike;
import com.nts.iot.modules.miniApp.model.RankByUser;
import com.nts.iot.modules.system.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author zhc@rnstec.com
 * @Description 订单管理
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */
public interface OrderManagerService extends IService<Order> {

    /**
     * 列表查询
     * @param pageable
     * @param order
     * @param jurisdictions
     * @return
     */
    Map queryAll(Pageable pageable, Order order, List<String> jurisdictions);

    /**
     *  关闭订单
     * @param lockBarcode 车锁编号
     */
    @Transactional(rollbackFor = Exception.class)
    Map<String, Object> closeOrder(String bikeBarcode, String lockBarcode, Long closeSource);

    /**
     *  创建订单
     * @param bikeBarcode 车编号
     * @param lockBarcode 车锁编号
     * @param maOpenId 小程序open id
     */
    @Transactional(rollbackFor = Exception.class)
    Order createOrder(String bikeBarcode, String lockBarcode,String maOpenId);

    /**
     * 根据用户查找轨迹
     * @param userId 用户
     * @param lockBarcode 车锁编号
     * @return
     */
//    List<CollectMessage> getTrajectoryByUserId(Long userId, String lockBarcode);

    /**
     * 获取订单轨迹等信息
     * @param orderId
     * @return
     */
    RidingTrack getRidingTrack(String orderId);

    /**
     * 订单轨迹长度
     *
     * @param orderId     订单编号
     * @param lockBarcode 锁编号
     * @return
     */
    Double getDistance(String orderId, String lockBarcode);

    List<Order> getRunningOrdersByBarcode(String lockBarCode);

    Order getRunningOrderByUserId(String userId);

    /**
     * 初始化订单缓存
     */
    void initOrderIds();

    /**
     * 根据锁号创建订单
     */
    Map<String, Object> createByDeviceNo(String deviceNo);

    /**
     * 根据锁号关闭订单
     */
    Map<String, Object> closeOrderByDeviceNo(String lockBarCode);

    /**
     * 小程序根据辖区查询排行榜
     */
    List<RankByUser> rankByJurisdiction(List<Long> jurisdictions, Long startTime, Long endTime);
    /**
     * 小程序根据辖区查询所有用户
     */
    List<RankByUser> rankByAllUser(List<Long> jurisdictions);

//    /**
//     * Pc根据辖区查询没有订单的用户
//     */
//    List<RankByUser> rankByAllUserPc(List<Long> jurisdictions);

    /**
     * 根据辖区查询排行榜PC
     */
    Map rankByJurisdictionPc(Page<RankByUser> page, List<Long> jurisdictions, Long startTime, Long endTime,Integer count);

    /**
     * 根据辖区查询排行榜PC   根据车辆统计
     */
    Map rankByBikeJurisdictionPc(Page<RankByBike> page, List<Long> jurisdictions, Long startTime, Long endTime);


}
