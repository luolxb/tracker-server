/*******************************************************************************
 * @(#)OrderServiceImpl.java 2019-05-03
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.service.impl;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.*;
import com.nts.iot.entity.Order;
import com.nts.iot.repository.OrderRepository;
import com.nts.iot.service.OrderService;
import com.nts.iot.util.EnclosureUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import com.nts.iot.util.RoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-03 10:22
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void saveByDeviceNo(String orderNo) {

        Order order = new Order();

        // 通过订单编号获得跟这个订单相关的先关订单key
        List<String> orderKeys = redisUtil.fuzzySearchRedis(RedisKey.ORDER_TRAJECTORY_KEY + orderNo + ":*");

        if (orderKeys != null && orderKeys.size() > 0) {
            String orderKey = orderKeys.get(0);

            List<String> orderInfoKeys = redisUtil.fuzzySearchRedis(RedisKey.ORDER_KEY + "*:" + orderNo);

            if (orderInfoKeys != null && orderInfoKeys.size() > 0) {
                String orderInfoKey = orderInfoKeys.get(0);
                // 根据定点编号获得订单信息
                OrderDto orderDto = JsonUtil.jsonConvertObject(redisUtil.getData(String.valueOf(orderInfoKey)), OrderDto.class);

                // 根据订单号查询订单其他信息，主要是获得辖区的编号
                Long jurisdiction = orderDto.getJurisdiction();

                // 根据辖区的编号获得这个辖区的必到点信息
                List<CheckPointDto> checkPoints = JsonUtil.jsonConvertList(redisUtil.getData(String.valueOf(jurisdiction)), CheckPointDto.class);

                // 获得缓存中的上传的采集点
                List<CollectMessage> messages = JsonUtil.jsonConvertList(redisUtil.getData(orderKey), CollectMessage.class);

                Collections.sort(messages);
                // TODO 这里的messages数据里面包含了离线包数据，所以这里可以考虑根据订单开始时间和截止时间将非订单范围内的坐标去掉之后放入ES

                // 比较每个必到点的信息跟轨迹的信息有多少个轨迹在这个必到点中，并且获得停留时间
                List<CheckPoint> points = new ArrayList<>();
                if (checkPoints != null) {
                    points = getCheckPoints(checkPoints, messages);
                }

                // 便利出道路
                List<Road> roads = RoadUtil.getRole(messages);

                // 获得行走的总和
                double distance = 0.0D;
                if (roads != null) {
                    distance = RoadUtil.getDistance(roads);
                }

                if (orderDto != null) {
                    // 车辆编号
                    order.setDeviceNo(orderDto.getBikeBarcode());
                    // 生成订单时间
                    order.setStartTime(orderDto.getStartTime());
                }

                Bike bike = JsonUtil.jsonConvertObject(redisUtil.getData(RedisKey.VECHILE_KEY + orderDto.getBikeBarcode()), Bike.class);
                if (bike != null) {
                    // 智能锁编号
                    order.setLockNo(bike.getLockBarcode());
                }

                // 订单编号
                order.setId(orderNo);
                // 总路程
                order.setDistance(distance);
                // 道路信息
                order.setRoads(roads);
                // 轨迹信息
                order.setTrajectories(RoadUtil.getTrajectory(messages));
                // 必到点信息
                order.setCheckPoints(points);
                // 结束时间
                order.setEndTime(System.currentTimeMillis());
                // 辖区编号
                order.setJurisdiction(jurisdiction);

                orderRepository.save(order);

                redisUtil.deleteByKey(orderKey);

            }

        }
    }

    @Override
    public RidingTrack findOne(String orderNo) {
        RidingTrack track = new RidingTrack();
        Order order = orderRepository.findById(orderNo).orElse(null);
        if (order != null) {
            List<Object> paths = new ArrayList<>();
            order.getTrajectories().forEach(trajectory -> {
                double[] point = new double[2];
                point[0] = Double.parseDouble(trajectory.getLongitude());
                point[1] = Double.parseDouble(trajectory.getLatitude());
                paths.add(point);
            });
            track.setPath(paths);
            track.setBikeBarcode(order.getDeviceNo());
            track.setDistance(order.getDistance());
            track.setStartTime(order.getStartTime());
            track.setEndTime(order.getEndTime());
            track.setOrderId(orderNo);
        }
        return track;
    }

    /**
     * 生成订单id
     *
     * @param deviceNo
     * @return
     */
 /*   public List<String> generateKey(String deviceNo, String begin,String end){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        List<String> myKeys = new ArrayList<>();

        try{

            Calendar startDay = Calendar.getInstance();
            startDay.setTime(format.parse(begin));
            startDay.add(Calendar.HOUR_OF_DAY, -1);

            while(true){
                startDay.add(Calendar.HOUR_OF_DAY, 1);
                Date newDate = startDay.getTime();
                String newstart=format.format(newDate);
                String newend = format.format(format.parse(end));
                myKeys.add(RedisKey.TRACKER_DEVICE_RECORD_TASK + newstart.replace("-","").replace(" ", ""));
                if(newend.equals(newstart)){
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return myKeys;
    }*/
    @Override
    public List<Order> queryAll(String deviceNo, Long startTime, Long endTime) {
        //查找订单
        Iterable<Order> orders = orderRepository.findAllByDeviceNoAndAndEndTimeBetween(deviceNo, startTime, endTime);

        List<Order> resultList = new ArrayList<>();
//        //生成订单id
//        List<String> ids = this.generateKey(deviceNo, startTime, endTime);
//        //查找订单
//        Iterable<Order> orders = orderRepository.findAllById(ids);

        orders.forEach(order -> {
            //过滤时间是否在查询时间之内
            if (!this.compareDate(order.getEndTime(), startTime, endTime)) {
                resultList.add(order);
            }
        });
        Collections.sort(resultList);
        return resultList;
    }

    /**
     * 根据订单List获得订单轨迹
     *
     * @param ids
     * @return
     */
    @Override
    public List<List<Map<String, Object>>> findAllByIdIn(List<String> ids) {
        // 返回结果
        List<List<Map<String, Object>>> result = new ArrayList<>();

        // 订单编号list判空
        if (ids != null && ids.size() > 0) {
//            List<Long> idList = JsonUtil.jsonConvertList(ids, Long.class);
            List<Long> idList = new ArrayList<>();
            for (String id : ids) {
                idList.add(Long.valueOf(id));
            }

            // 从es中查询订单list
            List<Order> orderList = orderRepository.findAllByIdIn(idList);

            // 订单list判空
            if (orderList != null && orderList.size() > 0) {

                // 循环所有订单
                for (int i = 0; i < orderList.size(); i++) {
                    // 获得一个订单
                    Order order = orderList.get(i);
                    // 获得订单下所有轨迹
                    List<Trajectory> trajectoryList = order.getTrajectories();

                    // 轨迹list判空
                    if (trajectoryList != null && trajectoryList.size() > 0) {
                        List<Map<String, Object>> trajList = new ArrayList<>();
                        // 循环轨迹list
                        for (int j = 0; j < trajectoryList.size(); j++) {
                            Map<String, Object> map = new HashMap<>();
                            // 获得轨迹对象
                            Trajectory trajectory = trajectoryList.get(j);
                            // 经度
                            map.put("longitude", trajectory.getLongitude());
                            // 纬度
                            map.put("latitude", trajectory.getLatitude());
                            trajList.add(map);
                        }
                        result.add(trajList);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<OrderBikeStaticDTO> getBikeStateByIds(List<OrderBikeQueryDTO> orderBikeQuerys) {
        List<OrderBikeStaticDTO> resultList = new ArrayList<>();
        for (OrderBikeQueryDTO orderQuery : orderBikeQuerys) {
            double runTime = 0d;
            double totalDistance = 0d;
            if (orderQuery.getOrderIds() != null && orderQuery.getOrderIds().size() > 0) {
                // 从es中查询订单list
                List<Order> orderList = orderRepository.findAllByIdIn(orderQuery.getOrderIds());
                for (Order order : orderList) {
                    /* 开始和结束时间不为空，且结束时间大于开始时间的时候，写入 */
                    if (order.getEndTime() != null && order.getStartTime() != null && (order.getEndTime() - order.getStartTime()) > 0) {
                        runTime = runTime + (order.getEndTime() - order.getStartTime());
                    }
                    totalDistance = totalDistance + order.getDistance();
                }
                // 米保留小数点后两位
                totalDistance = holdTwo(totalDistance);
                // 毫秒转换为分保留小数点后两位
                runTime = holdTwo(runTime / 1000 / 60);
            }
            OrderBikeStaticDTO result = new OrderBikeStaticDTO();
            result.setUserId(orderQuery.getUserId());
            result.setUserName(orderQuery.getUserName());
            result.setTotalDistance(totalDistance);
            result.setTotalRunTime(runTime);
            if (runTime > 0) {
                result.setAverageSpeed(holdTwo(totalDistance / runTime));
            } else {
                result.setAverageSpeed(0d);
            }
            resultList.add(result);
        }
        return resultList;
    }

    // double 保留小数后两位
    private Double holdTwo(Double num) {
        BigDecimal bg = new BigDecimal(num);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 比较时间
     *
     * @param time      订单结束时间
     * @param startTime 查询开始时间
     * @param endTime   查询结束时间
     * @return
     */
    private boolean compareDate(Long time, Long startTime, Long endTime) {
        return (time < startTime) || (time > endTime);
    }

    private List<CheckPoint> getCheckPoints(List<CheckPointDto> checkPointDtos, List<CollectMessage> messages) {
        List<CheckPoint> checkPoints = new ArrayList<>();
        for (CheckPointDto checkPointDto : checkPointDtos) {
            CheckPoint checkPoint = new CheckPoint();
            // 必到点编号
            checkPoint.setId(String.valueOf(checkPointDto.getId()));
            // 经度
            checkPoint.setLatitude(checkPointDto.getLatitude());
            // 维度
            checkPoint.setLongitude(checkPointDto.getLongitude());
            // 在必到点中获得轨迹的信息
            List<Trajectory> trajectories = getTrajectory(checkPointDto, messages);
            checkPoint.setTrajectories(trajectories);
            // 停留时间
            long stopTime = getStopTime(trajectories);
            checkPoint.setStopTime(stopTime);
            checkPoints.add(checkPoint);
        }

        return checkPoints;
    }

    private List<Trajectory> getTrajectory(CheckPointDto checkPointDto, List<CollectMessage> messages) {
        List<Trajectory> trajectories = new ArrayList<>();
        for (CollectMessage collectMessage : messages) {
            // 默认范围是50米
            if (EnclosureUtil.isInCircle(0.1, Double.parseDouble(checkPointDto.getLatitude()), Double.parseDouble(checkPointDto.getLongitude()), Double.parseDouble(collectMessage.getLatitude()), Double.parseDouble(collectMessage.getLongitude()))) {
                Trajectory trajectory = RoadUtil.initTrajectory(collectMessage);
                trajectories.add(trajectory);
            }
        }
        return trajectories;
    }

    /**
     * 算出停留时间，方法待优化
     *
     * @param trajectories
     * @return
     */
    private long getStopTime(List<Trajectory> trajectories) {
        Collections.sort(trajectories);
        if (trajectories.size() > 1) {
            long sum = 0L;
            for (int i = 0; i < trajectories.size() - 1; i++) {
                Trajectory before = trajectories.get(i);
                Trajectory after = trajectories.get(i + 1);
                sum = sum + (DateUtil.parse(after.getTime(), "yyyy-MM-DD HH:mm:ss").getTime() - DateUtil.parse(before.getTime(), "yyyy-MM-DD HH:mm:ss").getTime());
            }
            return sum;
        } else {
            return 0L;
        }
    }
}
