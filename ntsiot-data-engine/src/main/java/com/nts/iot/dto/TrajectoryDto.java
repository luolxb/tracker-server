package com.nts.iot.dto;

import lombok.Data;

@Data
public class TrajectoryDto implements Comparable<TrajectoryDto> {

    private String deviceNo;
    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 时间,YY-MM-DD- HH:mm:ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
     */
    private String time;


    /**
     * 无线型号强度
     */
    private String simSignal;

    /**
     * 毫秒
     */
    private Long mis;

    /**
     * 速度,1/10km/h
     */
    private String speed;

    /**
     * 方向,0～359，正北为0，顺时针
     */
    private String course;

    @Override
    public int compareTo(TrajectoryDto t) {
        Long diff = this.mis - t.getMis();
        return diff.intValue();
    }
}
