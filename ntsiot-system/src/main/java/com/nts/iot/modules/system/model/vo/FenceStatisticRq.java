package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("围栏统计请求参数")
public class FenceStatisticRq {

    @ApiModelProperty("开始时间【时间戳】")
    private Long startDate;

    @ApiModelProperty("结束时间【时间戳】")
    private Long endDate;

    @ApiModelProperty("围栏名称")
    private String fenceName;

    @ApiModelProperty("报警类型")
    private String fenceStatus;

    @ApiModelProperty("围栏类型")
    private String fenceType;

    @ApiModelProperty("设备编号")
    private String deviceNo;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Long userId;
}
