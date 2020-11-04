package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/4 10:36
 * @Description:
 */
@Data
public class StopPoint implements Serializable {

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 停留时间
     */
    private String stopTime;

    /**
     * 停留时长
     */
    private String timeLength;

    /**
     * 停留毫秒
     */
    private Long stopMs;


}
