package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @PackageName: com.nts.iot.modules.system.model
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:00
 **/

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("device")
public class Device extends Model<Device> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 型号
     */
    @TableField("device_type")
    private String deviceType;

    /**
     * 设备号(IMEI)或者(IMEI,SIM)
     */
    @TableField("device_no")
    private String deviceNo;

    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;

    /**
     * 图标
     */
    @TableField("device_logo")
    private String deviceLogo;

    /**
     * 备注
     */
    private String remark;
    private String password;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;

    /**
     * 是否启用 1：启用；2：禁用
     */
    private Integer enabled;

    /**
     * 是否删除1：否；2：是
     */
    private Integer delFlag;

    /**
     * 出厂时间
     */
    @TableField("leave_factory_time")
    private Date leaveFactoryTime;

    /**
     * 安装时间
     */
    @TableField("install_time")
    private Date installTime;

    /**
     * 平台到期时间
     */
    @TableField("platform_expires_time")
    private Date platformExpiresTime;


    /**
     * 用户到期时间
     */
    @TableField("user_expires_time")
    private Date userExpiresTime;

    /**
     * 设备状态 1：离线 2.在线
     */
    private Integer status;

    /**
     * 版本号
     */
    private String version;

    /**
     * 位置
     */
    private String location;
    /**
     * 定位时间
     */
    @TableField("gps_date")
    private Date gpsDate;

    /**
     * 装在设备内的手机卡号
     */
    @TableField("card_sim")
    private String cardSim;

    /**
     * 车牌号
     */
    @TableField("car_no")
    private String carNo;

    /**
     * 超速警报
     */
    @TableField("speed_alert")
    private Double speedAlert;

    /**
     * 超速持续时间
     */
    @TableField("speed_time")
    private Double speedTime;

    /**
     * 高温报警值
     */
    @TableField("high_temperature_alert")
    private Double highTemperatureAlert;

    /**
     * 低温报警值
     */
    @TableField("low_temperature_alert")
    private Double lowTemperatureAlert;

    @TableField("icc_id")
    private String iccId;

    /**
     * 车牌颜色
     */
    @TableField("car_no_colour")
    private String carNoColour;

    /**
     * 车架号
     */
    @TableField("frame_no")
    private String frameNo;

    /**
     * 发动机号
     */
    @TableField("engine_no")
    private String engineNo;

    /**
     * 车辆型号
     */
    @TableField("car_type")
    private String carType;

    /**
     * 激活码
     */
    @TableField("activation_code")
    private String activationCode;

    /**
     * 司机性别 1男  2女
     */
    private Integer gender;

    /**
     * 设备型号
     */
    private String type;

    /**
     * 汽车制造国
     */
    @TableField("car_made_country")
    private String carMadeCountry;

    /**
     * 车辆生产日期
     */
    @TableField("car_init_time")
    private Date carInitTime;

    /**
     * 最后在线时间
     */
    @TableField("last_on_line_time")
    private Date lastOnLineTime;


    /**
     * 设备激活 1：未激活 2.已激活
     */
    @TableField("activation")
    private Integer activation;

    /**
     * 激活时间
     */
    @TableField("activation_time")
    private Date activationTime;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    @TableField("contact_person")
    private String contactPerson;

    /**
     * 用户电话
     */
    @ApiModelProperty("用户电话")
    private String phone;

    /**
     * 静止时间
     */
    @ApiModelProperty("静止时间")
    @TableField("rest_time")
    private Date restTime;


}
