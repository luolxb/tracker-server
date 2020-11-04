package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@ApiModel("设备位置信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceLocationInfoVo {


    @ApiModelProperty("设备ID")
    private Long id;
    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String deviceNo;

    private String customName;


    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String deviceName;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String status;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String latitude;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String longitude;

    /**
     * 海拔高度，单位为米（m）
     */
    @ApiModelProperty("海拔高度，单位为米（m）")
    private String altitude;

    /**
     * 速度,1/10km/h
     */
    @ApiModelProperty("速度,1/10km/h")
    private String speed;

    /**
     * 方向,0～359，正北为0，顺时针
     */
    @ApiModelProperty("方向,0～359，正北为0，顺时针")
    private String course;

    /**
     * 设备上传时间
     */
    @ApiModelProperty("设备上传时间")
    private String time;

    /**
     * 无线型号强度
     */
    @ApiModelProperty("无线型号强度")
    private String simSignal;

    /**
     * 定位卫星数
     */
    @ApiModelProperty("定位卫星数")
    private String satellites;

    /**
     * 内部电量
     */
    @ApiModelProperty("内部电量")
    private String cellPower;

    /**
     * 内部电压
     */
    @ApiModelProperty("内部电压")
    private String cellVoltage;

    /**
     * 外部电量
     */
    @ApiModelProperty("外部电量")
    private String outCellPower;

    /**
     * 外部电压
     */
    @ApiModelProperty("外部电压")
    private String outCellVoltage;

    /**
     * 状态上报 0000 定时上报 0001 开锁上报。0010关锁上报。
     */
    @ApiModelProperty("状态上报 0000 定时上报 0001 开锁上报。0010关锁上报。")
    private String poiupState;

    /**
     * 锁梁状态
     */
    @ApiModelProperty("锁梁状态")
    private String beamState;

    /**
     * 服务器时间
     */
    @ApiModelProperty("服务器时间")
    private String systemTime;

    @ApiModelProperty("位置")
    private String location;

    @ApiModelProperty("图标")
    private String deviceLogo;

    @ApiModelProperty("激活状态 1：未激活  2：已激活")
    private Integer activation;

    /**
     * 静止时间
     */
    @ApiModelProperty("静止时间")
    private String restTime;

    @ApiModelProperty("最后在线时间")
    private String lastOnLineTime;

    @ApiModelProperty("地址")
    private String addr;

}
