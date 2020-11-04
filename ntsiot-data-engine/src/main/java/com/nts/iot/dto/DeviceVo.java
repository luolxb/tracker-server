package com.nts.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: com.nts.iot.modules.system.model.vo
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:27
 **/

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceVo implements Serializable {

    private Long id;

    /**
     * 所属客户
     */
    private Long customId;
    private String customName;

    /**
     * 型号
     */
    private String deviceType;

    /**
     * 设备号(IMEI)或者(IMEI,SIM)
     */
    private String deviceNo;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 图标
     */
    private String deviceLogo;

    /**
     * 备注
     */
    private String remark;

    private String password;


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
    private Date leaveFactoryTime;

    /**
     * 安装时间
     */
    private Date installTime;

    /**
     * 平台到期时间
     */
    private Date platformExpiresTime;


    /**
     * 用户到期时间
     */
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
    private Date gpsDate;

    /**
     * 装在设备内的手机卡号
     */
    private String cardSim;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 超速警报
     */
    private Double speedAlert;

    /**
     * 超速持续时间
     */
    private Double speedTime;

    /**
     * 高温报警值
     */
    private Double highTemperatureAlert;

    /**
     * 低温报警值
     */
    private Double lowTemperatureAlert;

    private String iccId;

    /**
     *车牌颜色
     */
    private String carNoColour;

    /**
     * 车架号
     */
    private String frameNo;

    /**
     * 发动机号
     */
    private String engineNo;

    /**
     * 车辆型号
     */
    private String carType;

    /**
     * 激活码
     */
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
    private String carMadeCountry;

    /**
     * 车辆生产日期
     */
    private Date carInitTime;

    /**
     * 最后在线时间
     */
    private Date lastOnLineTime;

    /**
     * 离线时长
     */
    private String offlineTime;

    /**
     * 设备激活 1：未激活 2.已激活
     */
    private Integer activation;

    /**
     * 激活时间
     */
    private Date activationTime;


    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 静止时间
     */
    private Date restTime;


    /**
     * 围栏ID
     */
    private Long fenceId;




}
