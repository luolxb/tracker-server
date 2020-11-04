package com.nts.iot.util;

import com.nts.iot.dto.CollectMessage;
import com.nts.iot.dto.TrajectoryDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/8/5 10:43
 * @Description:
 */

public class FilterCoordinatesUtil {

    /**
     * 过滤相同坐标，俩坐标之间有相同坐标，只保留GPS时间最小和最大的坐标
     *
     * @param list
     * @return
     */
    public static List<CollectMessage> filterCoordinates(List<CollectMessage> list) {
        List<CollectMessage> resultList = new ArrayList<>();
        String curr_lng = "";
        String curr_lat = "";
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            CollectMessage message = list.get(i);
            if ("".equals(curr_lng) && "".equals(curr_lat)) {
                // 初始坐标
                curr_lng = message.getLongitude();
                curr_lat = message.getLatitude();
                resultList.add(message);
            } else {
                // 坐标不相同，保存当前坐标和上一坐标
                if (!curr_lng.equals(message.getLongitude()) && !curr_lat.equals(message.getLatitude())) {
                    curr_lng = message.getLongitude();
                    curr_lat = message.getLatitude();
                    if (num > 0) {
                        // 保存相同坐标的GPS时间最大的坐标
                        resultList.add(list.get(i - 1));
                    }
                    // 当前坐标
                    resultList.add(message);
                    // 重置
                    num = 0;
                } else {
                    //相同坐标,累计计数
                    num++;
                }
            }
        }
        return resultList;
    }

    /**
     * 过滤相同坐标，俩坐标之间有相同坐标，只保留GPS时间最小和最大的坐标
     *
     * @param list
     * @return
     */
    public static List<TrajectoryDto> filterTrajectory(List<TrajectoryDto> list) {
        List<TrajectoryDto> resultList = new ArrayList<>();
        String curr_lng = "";
        String curr_lat = "";
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            TrajectoryDto message = list.get(i);
            if ("".equals(curr_lng) && "".equals(curr_lat)) {
                // 初始坐标
                curr_lng = message.getLongitude();
                curr_lat = message.getLatitude();
                resultList.add(message);
            } else {
                // 坐标不相同，保存当前坐标和上一坐标
                if (!curr_lng.equals(message.getLongitude()) && !curr_lat.equals(message.getLatitude())) {
                    curr_lng = message.getLongitude();
                    curr_lat = message.getLatitude();
                    if (num > 0) {
                        // 保存相同坐标的GPS时间最大的坐标
                        resultList.add(list.get(i - 1));
                    }
                    // 当前坐标
                    resultList.add(message);
                    // 重置
                    num = 0;
                } else {
                    //相同坐标,累计计数
                    num++;
                }
            }
        }
        return resultList;
    }

    public static void main(String args[]) {
        List<CollectMessage> list = new ArrayList<>();
        CollectMessage c = new CollectMessage();
        c.setLatitude("1");
        c.setLongitude("1");
        list.add(c);
        CollectMessage c1 = new CollectMessage();
        c1.setLatitude("2");
        c1.setLongitude("2");
        list.add(c1);
        CollectMessage c2 = new CollectMessage();
        c2.setLatitude("2");
        c2.setLongitude("2");
        list.add(c2);
        CollectMessage c3 = new CollectMessage();
        c3.setLatitude("3");
        c3.setLongitude("3");
        list.add(c3);
        List<CollectMessage> resultList = filterCoordinates(list);
        System.out.println(JsonUtil.getJson(resultList));
    }
}
