package com.nts.iot.manage;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.dto.CollectMessage;
import com.nts.iot.util.FilterCoordinatesUtil;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理
 * <p>
 * 根据车辆条码号获得这个车辆的订单号，然后根据订单号，将轨迹信息也存储到对应的集合中。
 * <p>
 * 注 ：缓存中没有不操作
 */
@Slf4j
@Component
public class OrderTrackManager {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加订单轨迹
     *
     * @param message 加入kafka的消息体
     */
    public void addOrderTrack(CollectMessage message) {
        // 根据车辆条码号获得这个车辆的订单号
        message.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        // TODO
        List<String> orderNoKeyList = redisUtil.fuzzySearchRedis(RedisKey.ORDER_KEY + message.getDeviceNo() + ":*");

       /* System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("key=================" + RedisKey.ORDER_KEY + message.getDeviceNo() + ":*");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");
        System.out.println("======================================");*/
        log.info("addOrderTrack key=================" + RedisKey.ORDER_KEY + message.getDeviceNo() + ":*");
        // 判空
        // TODO
        if (orderNoKeyList != null && orderNoKeyList.size() > 0) {
            // 循环所有的订单key
            for (int i = 0; i < orderNoKeyList.size(); i++) {
                String[] orderNoArray = orderNoKeyList.get(i).split(":");
                log.info("orderNoKeyList.get(i)=" + orderNoKeyList.get(i));
                // 获得redis key  订单号:设备编号
                String key = RedisKey.ORDER_TRAJECTORY_KEY + orderNoArray[3] + ":" + message.getDeviceNo();
                // 点 = 经度，纬度
//                String track = message.getLongitude() + "," + message.getLatitude();

                // 查询轨迹信息json
                String orderTrackInfo = redisUtil.getData(key);

                // 轨迹信息判空
                if (orderTrackInfo != null && !"".equals(orderTrackInfo)) {
                    List<CollectMessage> list = JsonUtil.jsonConvertList(orderTrackInfo, CollectMessage.class);
                    // 坐标点过滤
                    List<CollectMessage> resultList = FilterCoordinatesUtil.filterCoordinates(list);
//                    List<CollectMessage> cList = getSort(list);
                    // 添加轨迹
//                    list.add(message);
                    resultList.add(message);
                    // 加入到缓存中
                    redisUtil.addRedis(key, JsonUtil.getJson(resultList));
                } else {// 没有轨迹信息创建轨迹list
                    // 轨迹集合
                    List<CollectMessage> list = new ArrayList<>();
                    // 添加轨迹
                    list.add(message);
                    // 加入到缓存中
                    redisUtil.addRedis(key, JsonUtil.getJson(list));
                }
            }
        }
        // 没有订单信息不操作
    }

    /**
     * list排序
     *
     * @return
     */
    private static List<CollectMessage> getSort(List<CollectMessage> list) {
        // list判空
        if (list != null && list.size() > 0) {
            // 循环lsit
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.size() - 1 - i; j++) {
                    if (getTime(list.get(j).getTime()) > getTime(list.get(j + 1).getTime())) {
                        CollectMessage temp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set(j + 1, temp);
                    }
                }
            }
        }
        return list;
    }


    private static Long getTime(String date) {
        Long time = 0L;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


//    public static void main(String[] args) {
//        List<CollectMessage> collectMessageList = new ArrayList<>();
//        CollectMessage c1 = new CollectMessage();
//        c1.setTime("2019-05-22 18:30:31");
//        CollectMessage c2 = new CollectMessage();
//        c2.setTime("2019-05-22 18:10:31");
//        CollectMessage c3 = new CollectMessage();
//        c3.setTime("2019-05-22 18:26:31");
//        CollectMessage c4 = new CollectMessage();
//        c4.setTime("2019-05-22 18:50:31");
//        CollectMessage c5 = new CollectMessage();
//        c5.setTime("2019-05-22 18:01:31");
//
//        collectMessageList.add(c1);
//        collectMessageList.add(c2);
//        collectMessageList.add(c3);
//        collectMessageList.add(c4);
//        collectMessageList.add(c5);
//
//        List<CollectMessage> cList = getSort(collectMessageList);
//        for (int i = 0; i < cList.size(); i++) {
//            System.out.println(cList.get(i).getTime());
//        }
//
//    }
}
