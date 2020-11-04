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
 * 自行车轨迹管理
 * 根据年月日时:车辆条码号，将轨迹的信息添加到缓存中。每一个小时一条记录
 */
@Slf4j
@Component
public class TrackManager {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加轨迹
     *
     * @param message 加入kafka的消息体
     */
    public void addTrack(CollectMessage message) {
        message.setSystemTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        // 获得日期格式
        String date = subDate(message.getTime());
        log.info("addTrack key=================" + RedisKey.TRACKER_DEVICE_RECORD_TASK + date + ":" + message.getDeviceNo());
        // 判断时间格式是否有误
        if (!"".equals(date)) {
            // 获得redis key 年月日时:车辆条码号
            String key = RedisKey.TRACKER_DEVICE_RECORD_TASK + date + ":" + message.getDeviceNo();
            // 点 = 经度，纬度
//            String pointJson = message.getLongitude() + "," + message.getLatitude();
            // 判断是否是当前小时内的时间
            if (getDate().equals(date)) {
                // 查询轨迹信息json
                String trackInfoJson = redisUtil.getData(key);
                // 轨迹信息判空
                if (trackInfoJson != null && !"".equals(trackInfoJson)) {
                    // 获得缓存中的数据
                    List<CollectMessage> list = JsonUtil.jsonConvertList(trackInfoJson, CollectMessage.class);
                    // 加入到缓存中
                    /* 判断，如果前面点的 经纬度相同，则删除前面的点 */
//                    for (int i = list.size() - 1; i >= 0; i--) {
//                        CollectMessage collect = list.get(i);
//                        /* 经纬度相同 */
//                        if (message.getLatitude().equals(collect.getLatitude())&&message.getLongitude().equals(collect.getLongitude())) {
//                            // 删除与此相同的经纬度坐标点
//                            list.remove(collect);
//                        }else {
//                            break;
//                        }
//                    }
                    // 坐标点过滤
                    List<CollectMessage> resultList = FilterCoordinatesUtil.filterCoordinates(list);
                    // 添加轨迹
//                    list.add(message);
                    resultList.add(message);
                    // 加入到redis
                    redisUtil.addRedis(key, JsonUtil.getJson(resultList));
                } else {// 为空创建轨迹list插入到缓存中
                    // 轨迹集合
                    List<CollectMessage> list = new ArrayList<>();
                    // 添加轨迹
                    list.add(message);
                    // 加入到缓存中
                    redisUtil.addRedis(key, JsonUtil.getJson(list));
                }
            } else {// 当前小时外的新建轨迹缓存
                // 轨迹集合
                List<CollectMessage> list = new ArrayList<>();
                // 添加轨迹
                list.add(message);
                // 加入到缓存中
                redisUtil.addRedis(key, JsonUtil.getJson(list));
            }
        } else {
            log.info("添加车辆轨迹，时间格式有误！！！");
            //System.out.println("添加车辆轨迹，时间格式有误！！！");
        }
    }

//    public static void main(String[] args) {
//        List<Integer> data = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,9,9,9,9,9,9,9));
//        Integer max = 9;
//        for (int i = data.size() - 1; i >= 0; i--) {
//            if (max.equals(data.get(i))) {
//                data.remove(data.get(i));
//            }else {
//                break;
//            }
//        }
//        System.out.println(data.size());
//    }


    /**
     * 截取日期
     *
     * @param data
     * @return
     */
    private String subDate(String data) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long time = sdf.parse(data).getTime();
            System.out.println(time);
            SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            str = dataSdf.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 获得当前时间的 YY-MM-dd-HH
     *
     * @return
     */
    private static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
        return sdf.format(new Date(System.currentTimeMillis()));
    }


//    //测试车辆轨迹时间格式
//    public static void main(String[] args) {
////        System.out.println(subDate("YY-MM-DD-hh-mm-ss"));
//        System.out.println(subDate("2019-05-21 18:37:51"));
//    }


}
