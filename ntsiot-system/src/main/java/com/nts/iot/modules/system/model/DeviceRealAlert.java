package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @PackageName: com.nts.iot.modules.system.model
 * @program: ntsiot
 * @author: ruosen
 * @create: 2020-06-22 18:28
 **/
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
@TableName("device_real_alert")
public class DeviceRealAlert {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备ID
     */
    @TableField("device_no")
    private String deviceNo;

    /**
     * 设备报警类型
     */
    @TableField("alert_type")
    private String alertType;

    /**
     * 删除标记 1：正常  2：删除
     */
    @TableField("del_flag")
    private Long delFlag;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;

    private Integer status;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 速度,1/10km/h
     */
    private String speed;

    /**
     * 方向,0～359，正北为0，顺时针
     */
    private String course;

    /**
     * 时间,YY-MM-DD-hh-mm-ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
     */
    private Date time;

    /**
     * 设备名称
     */
    @TableField(exist = false)
    private String deviceName;
}

