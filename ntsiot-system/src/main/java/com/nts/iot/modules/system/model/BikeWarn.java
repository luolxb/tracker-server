package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


/**
 * @Author zzm@rnstec.com
 * @Description 车辆告警表
 * @Date 2019-07-17 14:31
 * @Version: 1.0
 */

@TableName("t_bike_warn")
@Data
public class BikeWarn extends Model<BikeWarn> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车辆条码
     */
    @TableField(value = "bike_barcode")
    private String bikeBarcode;

    /**
     * 智能锁条码
     */
    @TableField(value = "lock_barcode")
    private String lockBarcode;

    /**
     * 所属辖区
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 告警信息
     */
    @TableField(value = "message")
    private String message;


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
     * 发送时间
     */
    @TableField(value = "time")
    private Long time;


}
