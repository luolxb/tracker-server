package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 自行车信息
 */
@Data
public class BikeInfo implements Serializable {
    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;
}
