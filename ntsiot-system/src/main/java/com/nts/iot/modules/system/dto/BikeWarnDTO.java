package com.nts.iot.modules.system.dto;

import lombok.Data;


/**
 * @Author zzm@rnstec.com
 * @Description 车辆告警表
 * @Date 2019-07-17 14:31
 * @Version: 1.0
 */

@Data
public class BikeWarnDTO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 车辆条码
     */
    private String bikeBarcode;

    /**
     * 智能锁条码
     */
    private String lockBarcode;

    /**
     * 所属辖区
     */
    private Long deptId;

    /**
     * 告警信息
     */
    private String message;


    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;


    /**
     * 发送时间
     */
    private Long time;


    /**
     * 开始发送时间
     */
    private Long startTime;


    /**
     * 结束发送时间
     */
    private Long endTime;

}
