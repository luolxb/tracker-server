package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 辖区配置模块
 */
@TableName("jurisdiction_configuration")
@Data
@ApiModel("辖区配置模块")
public class JurisdictionConfiguration extends Model<JurisdictionConfiguration> {
    /**
     * 主键id
     */
    @ApiModelProperty("辖区配置模块")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * logo
     */
    @ApiModelProperty("辖区配置模块logourl")
    @TableField(value = "logo_url")
    private String logoUrl;

    /**
     * 是否开启车辆授权
     */
    @ApiModelProperty("是否开启车辆授权")
    @TableField(value = "is_vehicle_license")
    private String isVehicleLicense;

    /**
     * 是否开始虚拟装判断
     */
    @ApiModelProperty("是否开始虚拟装判断")
    @TableField(value = "is_virtual")
    private String isVirtual;

    /**
     * 是否开启限行区域停车判断
     */
    @ApiModelProperty("是否开启限行区域停车判断")
    @TableField(value = "is_parking_restrictions")
    private String isParkingRestrictions;

    /**
     * 辖区电话
     */
    @ApiModelProperty("辖区电话")
    @TableField(value = "phone")
    private String phone;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 辖区名称
     */
    @ApiModelProperty("辖区名称")
    @TableField(value = "name")
    private String name;

    /**
     * 辖区编号
     */
    @ApiModelProperty("辖区编号")
    @TableField(value = "jurisdiction")
    private Long jurisdiction;

    /**
     * 电动车
     */
    @ApiModelProperty("电动车")
    @TableField("bike_Icon1")
    private String bikeIcon1;

    /**
     * 助力车
     */
    @ApiModelProperty("助力车")
    @TableField("bike_Icon2")
    private String bikeIcon2;

    /**
     * 开始时间的间隔天数
     */
    @ApiModelProperty("开始时间的间隔天数")
    @TableField("other_day")
    private Long otherDay;

    /**
     * 查询开始时间
     */
    @ApiModelProperty("查询开始时间")
    @TableField("start_time")
    private String startTime;

    /**
     * 结束时间的间隔天数
     */
    @ApiModelProperty("结束时间的间隔天数")
    @TableField("next_day")
    private Long nextDay;

    /**
     * 查询结束时间
     */
    @ApiModelProperty("查询结束时间")
    @TableField("end_time")
    private String endTime;


    /**
     * 停留点（30分钟以内）
     */
    @ApiModelProperty("停留点（30分钟以内）")
    @TableField(value = "stop_marker1")
    private String stopMarker1;

    /**
     * 停留点（30~60分钟以内）
     */
    @ApiModelProperty("停留点（30~60分钟以内）")
    @TableField(value = "stop_marker2")
    private String stopMarker2;

    /**
     * 停留点（60分钟以外）
     */
    @ApiModelProperty("停留点（60分钟以外）")
    @TableField(value = "stop_marker3")
    private String stopMarker3;

    /**
     * 是否显示小尾巴
     */
    @ApiModelProperty("是否显示小尾巴")
    @TableField(value = "show_real_line")
    private String showRealLine;

    /**
     * 小尾巴颜色
     */
    @ApiModelProperty("小尾巴颜色")
    @TableField(value = "color")
    private String color;


    /**
     * 安全守护
     */
    @ApiModelProperty("安全守护")
    @TableField(value = "security_guard")
    private String securityGuard;


}
