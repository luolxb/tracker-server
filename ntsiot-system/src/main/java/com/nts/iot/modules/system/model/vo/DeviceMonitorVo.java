package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel("设备请求实体类监控平台设备VO")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceMonitorVo {


    @ApiModelProperty("全部")
    List<DeviceLocationInfoVo> deviceAll;

    @ApiModelProperty("在线")
    List<DeviceLocationInfoVo> deviceOnLine;

    @ApiModelProperty("离线")
    List<DeviceLocationInfoVo> deviceOffLine;

    @ApiModelProperty("未启用")
    List<DeviceLocationInfoVo> unEnable;


}
