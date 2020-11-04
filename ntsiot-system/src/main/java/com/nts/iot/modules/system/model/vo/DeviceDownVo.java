package com.nts.iot.modules.system.model.vo;

import com.nts.iot.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @PackageName: com.nts.iot.modules.system.model.vo
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:27
 **/

@ApiModel("设备下载/导出实体类")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceDownVo implements Serializable {

    /**
     * 型号
     */
    @Excel(name = "设备类型")
    @ApiModelProperty("型号")
    private String deviceType;

    /**
     * 设备号(IMEI)或者(IMEI,SIM)
     */
    @Excel(name = "设备号/设备ID")
    @ApiModelProperty("设备号(IMEI)或者(IMEI,SIM)")
    private String deviceNo;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    @ApiModelProperty("设备名称")
    private String deviceName;

    /**
     * 安装时间
     */
    @Excel(name = "安装时间",dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("安装时间")
    private Date installTime;

    /**
     * 平台到期时间
     */
    @Excel(name = "平台到期时间",dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("平台到期时间")
    private Date platformExpiresTime;


    /**
     * 用户到期时间
     */
    @Excel(name = "用户到期时间",dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("用户到期时间")
    private Date userExpiresTime;

    /**
     * 装在设备内的手机卡号
     */
    @Excel(name = "设备SIM")
    @ApiModelProperty("装在设备内的手机卡号")
    private String cardSim;

    /**
     * 车牌号
     */
    @Excel(name = "车牌号")
    @ApiModelProperty("车牌号")
    private String carNo;

    /**
     * 联系人
     */
    @Excel(name = "车主姓名")
    @ApiModelProperty("联系人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Excel(name = "车主电话")
    @ApiModelProperty("联系电话")
    private String phone;
}
