package com.nts.iot.service.impl;

import com.nts.iot.dto.CheckPoint;
import com.nts.iot.entity.Order;
import com.nts.iot.entity.Security;
import com.nts.iot.repository.OrderRepository;
import com.nts.iot.repository.SecurityRepository;
import com.nts.iot.service.StatisticsBikeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsBikeInfoServiceImpl implements StatisticsBikeInfoService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SecurityRepository securityRepository;

    /**
     * 统计车辆信息
     */
    @Override
    public List<Security> statisticsBikeInfo() {
        Long startTime = getYesterdayTime(" 00:00:00");
        Long endTime = getYesterdayTime(" 23:59:59");
        // 查询前一天所有订单
        List<Order> orderList = orderRepository.findAllByEndTimeBetween(startTime, endTime);
        // 得到返回结果
        List<Security> securityList = sum(orderList, startTime, endTime);
        // 插入到es
        if (securityList != null && securityList.size() > 0) {
            securityRepository.saveAll(securityList);
        }
        return securityList;
    }

    /**
     * 通过辖区查询Security
     *
     * @param jurisdiction 辖区id
     * @return
     */
    @Override
    public Security findAllByJurisdiction(String jurisdiction) {
        // 年-月-日-辖区id
        String id=getData() + "-" + jurisdiction;
        return securityRepository.findById(id).orElse(null);
    }


    /**
     * 统计求和
     *
     * @param orderList
     */
    private List<Security> sum(List<Order> orderList, Long startTime, Long endTime) {
        List<Security> securityList = null;
        // 订单判空
        if (orderList != null && orderList.size() > 0) {
            // 声明数据类型
            Map<Long, List<Order>> map = new HashMap<>();
            for (int i = 0; i < orderList.size(); i++) {
                // 获得辖区编号
                Long jurisdiction = orderList.get(i).getJurisdiction();
                // 判断车辆编号是否在map中存在  key：辖区编号，value：订单对象
                if (map.containsKey(jurisdiction)) {
                    // 存入map中
                    List<Order> orders = map.get(jurisdiction);
                    orders.add(orderList.get(i));
                    map.put(jurisdiction, orders);
                }
                // 不包含 存入map中
                else {
                    List<Order> ordList = new ArrayList<>();
                    ordList.add(orderList.get(i));
                    map.put(jurisdiction, ordList);
                }
            }
            // 获得 List<Security>
            securityList = getSecurity(map, startTime, endTime);
        }
        return securityList;
    }

    /**
     * 获得 Security 对象
     *
     * @param map
     * @param startTime
     * @param endTime
     * @return
     */
    private List<Security> getSecurity(Map<Long, List<Order>> map, Long startTime, Long endTime) {
        List<Security> securityList = new ArrayList<>();
        //map 判空
        if (map != null && map.size() > 0) {
            //遍历map中的键
            for (Map.Entry<Long, List<Order>> entry : map.entrySet()) {
                Security security = new Security();
                Map<String, List<String>> securityMap = new HashMap<>();
                // 总里程数
                Double distance = 0.0;
                // 停留时间
                Double stopTime = 0.0;
                // 车辆编号
                Long jurisdiction = entry.getKey();
                // 订单对象
                List<Order> orderList = entry.getValue();

                // 订单结合判空
                if (orderList != null && orderList.size() > 0) {
                    // 循环辖区下所有自行车一天中生成的所有订单
                    for (int i = 0; i < orderList.size(); i++) {
                        Order order = orderList.get(i);
                        // 循环加总里程数
                        distance += order.getDistance();
                        // 循环加总停留时间
                        stopTime += getStopTime(order.getCheckPoints());
                        // 获得车辆编号
                        String deviceNo = order.getDeviceNo();

                        // 判断车辆编号是否在map中存在  key:车辆编号，value：订单对象
                        if (securityMap.containsKey(deviceNo)) {
                            // 存入map中
                            List<String> orders = securityMap.get(deviceNo);
                            orders.add(order.getId());
                            securityMap.put(deviceNo, orders);
                        }
                        // 不包含 存入map中
                        else {
                            List<String> ordList = new ArrayList<>();
                            ordList.add(order.getId());
                            securityMap.put(deviceNo, ordList);
                        }
                    }
                }

                security.setId(getData() + "-" + jurisdiction);
                // 开始时间
                security.setBeginTime(String.valueOf(startTime));
                // 结束时间
                security.setEndTime(String.valueOf(endTime));
                // 总里程数
                security.setDistance(String.valueOf(distance));
                // 总停留时间
                security.setStopTime(String.valueOf(stopTime));
                // 辖区编号
                security.setJurisdiction(String.valueOf(jurisdiction));
                // 车辆和订单的的结果集
                security.setMap(securityMap);

                securityList.add(security);
            }
        }
        return securityList;
    }

    /**
     * 获得停留时间
     *
     * @param checkPoints
     * @return
     */
    private Double getStopTime(List<CheckPoint> checkPoints) {
        // 停留时间
        Double stopTime = 0.0;
        if (checkPoints != null && checkPoints.size() > 0) {
            for (int i = 0; i < checkPoints.size(); i++) {
                stopTime += checkPoints.get(i).getStopTime();
            }
        }
        return stopTime;
    }

    /**
     * 获得前一天的时间戳
     *
     * @param postfix
     * @return
     */
    private Long getYesterdayTime(String postfix) {
        Long time = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date d = cal.getTime();
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
            String data = sp.format(d);//获取昨天日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = sdf.parse(data + postfix).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private String getData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date d = cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(d);//获取昨天日期
    }
}
