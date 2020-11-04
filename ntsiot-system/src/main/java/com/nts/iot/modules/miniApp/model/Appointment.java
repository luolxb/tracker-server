package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 预约登记
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/29 10:23
 * @Description:
 */
@Data
@TableName("appointment")
public class Appointment extends Model<Appointment> {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String username;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 辖区
     */
    private Long jurisdiction;

    /**
     * 预约日期
     */
    @TableField("appointment_date")
    private String appointmentDate;

    /**
     * 预约时间
     */
    @TableField("appointment_time")
    private String appointmentTime;

    /**
     * 现居地
     */
    private String address;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Long updateTime;

    /**
     * 受理状态
     */
    private String status;

    /**
     * 小程序open id
     */
    @TableField("ma_open_id")
    private String openId;
}
