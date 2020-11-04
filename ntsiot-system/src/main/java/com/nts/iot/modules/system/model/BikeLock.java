package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@TableName("t_bike_lock")
@Data
public class BikeLock extends Model<BikeLock> {

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
     * 车锁编码
     */
    @TableField(value = "lock_barcode")
    private String lockBarcode;

    /**
     * 是否删除
     */
    @TableField(value = "is_del")
    private String isDel;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;
}
