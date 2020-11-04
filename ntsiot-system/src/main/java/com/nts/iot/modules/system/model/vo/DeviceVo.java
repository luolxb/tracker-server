package com.nts.iot.modules.system.model.vo;

import com.nts.iot.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: com.nts.iot.modules.system.model.vo
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:27
 **/

@ApiModel("设备请求实体类")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceVo implements Serializable {

    @ApiModelProperty("设备id")
    private Long id;

    /**
     * 所属客户
     */
    @ApiModelProperty("所属客户")
    private Long customId;
    @Excel(name = "所属客户")
    private String customName;

    /**
     * 型号
     */
    @Excel(name = "型号")
    @ApiModelProperty("型号")
    private String deviceType;

    /**
     * 设备号(IMEI)或者(IMEI,SIM)
     */
    @Excel(name = "设备号(IMEI)或者(IMEI,SIM)")
    @ApiModelProperty("设备号(IMEI)或者(IMEI,SIM)")
    private String deviceNo;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    @ApiModelProperty("设备名称")
    private String deviceName;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String deviceLogo;

    /**
     * 备注
     */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("密码")
    @Size(max = 20, message = "密码字符长度应该在20以内")
    private String password;


    /**
     * 是否启用 1：启用；2：禁用
     */
    @ApiModelProperty("是否启用 1：启用；2：禁用")
    private Integer enabled;

    /**
     * 是否删除1：否；2：是
     */
    @ApiModelProperty("是否删除1：否；2：是")
    private Integer delFlag;


    /**
     * 出厂时间
     */
    @Excel(name = "出厂时间",dateFormat = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("出厂时间")
    private Date leaveFactoryTime;

    /**
     * 安装时间
     */
    @Excel(name = "安装时间",dateFormat = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("安装时间")
    private Date installTime;

    /**
     * 平台到期时间
     */
    @Excel(name = "平台到期时间",dateFormat = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("平台到期时间")
    private Date platformExpiresTime;


    /**
     * 用户到期时间
     */
    @Excel(name = "用户到期时间",dateFormat = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("用户到期时间")
    private Date userExpiresTime;


    /**
     * 设备状态 1：离线 2.在线
     */
    @Excel(name = "设备状态 1：离线 2.在线")
    @ApiModelProperty("设备状态 1：离线 2.在线")
    private Integer status;

    /**
     * 版本号
     */
    @Excel(name = "版本号")
    @ApiModelProperty("版本号")
    private String version;

    /**
     * 位置
     */
    @ApiModelProperty("位置")
    private String location;
    /**
     * 定位时间
     */
    @ApiModelProperty("定位时间")
    private Date gpsDate;

    /**
     * 装在设备内的手机卡号
     */
    @Excel(name = "装在设备内的手机卡号")
    @ApiModelProperty("装在设备内的手机卡号")
    private String cardSim;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号")
    @ApiModelProperty("车牌号")
    private String carNo;

    /**
     * 超速警报
     */
    @ApiModelProperty("超速警报")
    private Double speedAlert;

    /**
     * 超速持续时间
     */
    @ApiModelProperty("超速持续时间")
    private Double speedTime;

    /**
     * 高温报警值
     */
    @ApiModelProperty("高温报警值")
    private Double highTemperatureAlert;

    /**
     * 低温报警值
     */
    @ApiModelProperty("低温报警值")
    private Double lowTemperatureAlert;

    @ApiModelProperty("iccId")
    private String iccId;

    /**
     *车牌颜色
     */
    @ApiModelProperty("车牌颜色")
    private String carNoColour;

    /**
     * 车架号
     */
    @ApiModelProperty("车架号")
    private String frameNo;

    /**
     * 发动机号
     */
    @ApiModelProperty("发动机号")
    private String engineNo;

    /**
     * 车辆型号
     */
    @ApiModelProperty("车辆型号")
    private String carType;

    /**
     * 激活码
     */
    @ApiModelProperty("激活码")
    private String activationCode;

    /**
     * 司机性别 1男  2女
     */
    @ApiModelProperty("司机性别 1男  2女")
    private Integer gender;

    /**
     * 设备型号
     */
    @ApiModelProperty("设备型号")
    private String type;

    /**
     * 汽车制造国
     */
    @ApiModelProperty("汽车制造国")
    private String carMadeCountry;

    /**
     * 车辆生产日期
     */
    @ApiModelProperty("车辆生产日期")
    private Date carInitTime;

    /**
     * 最后在线时间
     */
    @ApiModelProperty("最后在线时间")
    @Excel(name = "最后在线时间")
    private Date lastOnLineTime;

    /**
     * 离线时长
     */
    @ApiModelProperty("离线时长")
    @Excel(name = "离线时长")
    private String offlineTime;

    /**
     * 设备激活 1：未激活 2.已激活
     */
    @ApiModelProperty("设备激活 1：未激活 2.已激活")
    private Integer activation;

    /**
     * 激活时间
     */
    @ApiModelProperty("激活时间")
    private Date activationTime;


    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String phone;

    /**
     * 静止时间
     */
    @ApiModelProperty("静止时间")
    private Date restTime;


    /**
     * 围栏ID
     */
    @ApiModelProperty("围栏ID")
    private Long fenceId;



}
