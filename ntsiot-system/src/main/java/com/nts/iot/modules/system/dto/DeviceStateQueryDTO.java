package com.nts.iot.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆运行(订单)统计
 */
@Data
@ApiModel("运行(订单)统计")
public class DeviceStateQueryDTO implements Serializable {

    @ApiModelProperty("开始时间【时间戳】")
    private Long startTime;

    @ApiModelProperty("结束时间【时间戳】")
    private Long endTime;
    //  设备编号list
    @ApiModelProperty(" 设备编号list")
    private List<String> deviceNo = new ArrayList<>();

}
