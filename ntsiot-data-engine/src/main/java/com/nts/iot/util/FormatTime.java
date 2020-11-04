package com.nts.iot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormatTime {

    // 一小时等于 60*60*1000= 3600000 毫秒
    private static Long mis = 3600000L;

    /**
     * 将日期分段
     *
     * @return
     */
    public static List<String> formatTime(Long startTime, Long endTime) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= getTimeDifference(startTime, endTime); i++) {
            // 加入开始日期
            if (i == 1) {
                list.add(getYMDH(startTime));
//                System.out.println(getYMDH(startTime));
            }
            list.add(getYMDH(startTime + mis * i));
//            System.out.println(getYMDH(startTime + mis * i));
        }
        return list;
    }

    /**
     * 将日期转时间戳
     *
     * @return
     */
    public static Long dateToTime(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获得年月日时 如 2019-07-11-15
     *
     * @param time
     * @return
     */
    private static String getYMDH(Long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 计算跨度多少个小时
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private static Long getTimeDifference(Long startTime, Long endTime) {
        Long hour = 1L;
        // 毫秒差
        Long mistiming = endTime - startTime;
        // 超过1小时
        if (mistiming > mis) {
            Long hourLong = mistiming / mis;
            if (hourLong > 0) {
                hour = hourLong;
            }
        }
        return hour;
    }

    /**
     * 转换函数
     * @param ms 毫秒
     * @return
     */
    public static String longTimeToDay(Long ms){
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuilder sb = new StringBuilder();
        if(day > 0) {
            sb.append(day).append("天");
        }
        if(hour > 0) {
            sb.append(hour).append("小时");
        }
        if(minute > 0) {
            sb.append(minute).append("分");
        }
        if(second > 0) {
            sb.append(second).append("秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond).append("毫秒");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        Long startTime = 1562816275000L;
//        Long endTime = 1562816925000L;
//        formatTime(startTime, endTime);

//        System.out.println(dateToTime("2019-05-24 16:20:31"));
    }


}
