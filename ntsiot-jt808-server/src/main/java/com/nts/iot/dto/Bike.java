package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zhc@rnstec.com
 * @Description 车辆表
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */


@Data
public class Bike implements Serializable {

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
    private Long jurisdiction;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    /**
     * 辖区名称
     */
    private String deptName;

    /**
     * 鉴权码
     */
    private String registerNo;


    /**
     * sim卡iccid号
     */
    private String iccid;


}
