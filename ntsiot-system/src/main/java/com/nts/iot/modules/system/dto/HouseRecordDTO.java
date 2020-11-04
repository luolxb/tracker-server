package com.nts.iot.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:26
 * @Description:
 */
@Data
public class HouseRecordDTO implements Serializable {

    private Long id;


    /**
     * 房东
     */
    private String owner;

    /**
     * 房东电话
     */
    private String ownerPhone;

    /**
     * 所属辖区
     */
    private Long deptId;

    /**
     * 地址
     */
    private String address;

    /**
     * 录入时间
     */
    private Long createTime;

    /**
     * 租客
     */
    private String tenant;

    /**
     * 租客电话
     */
    private String tenantPhone;

    /**
     * 租客身份证
     */
    private String tenantIdcard;

    private String inputTime;

}
