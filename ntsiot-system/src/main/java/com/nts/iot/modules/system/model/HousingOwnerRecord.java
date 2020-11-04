package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:08
 * @Description: 房屋房东备案表
 */
@TableName("housing_owner_record")
@Data
public class HousingOwnerRecord extends Model<HousingOwnerRecord> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房东
     */
    private String owner;

    /**
     * 房东电话
     */
    @TableField("owner_phone")
    private String ownerPhone;

    /**
     * 所属辖区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 地址
     */
    private String address;

    @TableField("create_time")
    private Long crateTime;

    @TableField("update_time")
    private Long updateTime;

}
