package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@TableName("t_lock")
@Data
public class Lock extends Model<Lock> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车锁编码
     */
    @TableField(value = "lock_barcode")
    private String lockBarcode;

    /**
     * sim卡的iccid号
     */
    @TableField(value = "sim_iccid_no")
    private String simIccidNo;

    /**
     * 锁蓝牙mac地址
     */
    @TableField(value = "mac_address")
    private String macAddress;

    /**
     * 设备注册授权码
     */
    @TableField(value = "authorization_code")
    private String authorizationCode;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 更新者
     */
    @TableField(value = "updater")
    private String updater;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 是否使用
     */
    @TableField(value = "is_use")
    private String isUse;

    /**
     * 上报时间
     */
    @TableField(exist = false)
    private String time;
}
