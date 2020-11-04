package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: hamsun
 * @Description: 预约登记管理人
 * @Date: 2019/10/27 18:41
 */
@Data
@TableName("appointment_manager")
public class AppointmentManager extends Model<AppointmentManager> {
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
     * 短信开关,1:开启，0关闭
     */
    @TableField("switch")
    private Integer switchFlag;

    /**
     * 短信发送周期,0:每天，1：每周，2：每月
     */
    private Integer period;

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

}
