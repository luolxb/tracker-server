package com.nts.iot.modules.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("围栏统计显示参数")
public class FenceStatisticVo {

    @ApiModelProperty("定位")
    private String coordinate;

    @ApiModelProperty("定位给时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Long alertTime;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("围栏类型")
    private String type;

    @ApiModelProperty("报警类型")
    private String status;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("围栏名称")
    private String fenceName;

    @ApiModelProperty("设备编号")
    private String deviceNo;


}
