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
 * @Description: 房屋房客备案表
 */
@TableName("housing_tenant_record")
@Data
public class HousingTenantRecord extends Model<HousingTenantRecord> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房客
     */
    private String tenant;

    /**
     * 房客电话
     */
    @TableField("tenant_phone")
    private String tenantPhone;

    /**
     * 房客身份证号
     */
    @TableField("tenant_idcard")
    private String tenantIdcard;

    /**
     * 房主记录ID
     */
    @TableField("house_id")
    private Long houseId;


    @TableField("create_time")
    private Long crateTime;

    @TableField("update_time")
    private Long updateTime;

}
