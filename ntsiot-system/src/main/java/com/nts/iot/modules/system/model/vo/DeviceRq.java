package com.nts.iot.modules.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class DeviceRq {


    @ApiModelProperty("设备id")
    private Long id;

    @ApiModelProperty("销售给用户id")
    private Long targetId;

    @ApiModelProperty("销售给用户name")
    private String  targetName;
    /**
     * 所属客户
     */
    @NotNull(message = "所属客户不能为空")
    @ApiModelProperty("所属客户")
    private Long customId;


    /**
     * 型号
     */
    @NotBlank(message = "型号不能为空")
    @ApiModelProperty("型号")
    private String deviceType;

    /**
     * 设备号(IMEI)或者(IMEI,SIM)
     */
    @NotBlank(message = "设备号(IMEI)或者(IMEI,SIM) 不能为空")
    @ApiModelProperty("设备号(IMEI)或者(IMEI,SIM)")
    private String deviceNo;

    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称 不能为空")
    @ApiModelProperty("设备名称")
    private String deviceName;

    /**
     * 图标
     */
    @NotNull(message = "图标不能为空")
    @ApiModelProperty("图标")
    private String deviceLogo;

    /**
     * 备注
     */
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
    @ApiModelProperty("出厂时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaveFactoryTime;

    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date installTime;

    /**
     * 平台到期时间
     */
    @ApiModelProperty("平台到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date platformExpiresTime;


    /**
     * 用户到期时间
     */
    @ApiModelProperty("用户到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userExpiresTime;

    /**
     * 设备状态 1：离线 2.在线
     */
    @ApiModelProperty("设备状态 1：离线 2.在线")
    private Integer status;

    /**
     * 版本号
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gpsDate;

    /**
     * 装在设备内的手机卡号
     */
    @ApiModelProperty("装在设备内的手机卡号")
    private String cardSim;

    /**
     * 车牌号
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date carInitTime;

    /**
     * 最后在线时间
     */
    @ApiModelProperty("最后在线时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastOnLineTime;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contactPerson;

    /**
     * 用户电话
     */
    @ApiModelProperty("用户电话")
    private String phone;


}
