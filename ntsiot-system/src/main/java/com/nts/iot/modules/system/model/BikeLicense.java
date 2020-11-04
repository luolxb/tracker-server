package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author zhc@rnstec.com
 * @Description 车辆授权
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */

@TableName("bike_license")
@Data
public class BikeLicense extends Model<BikeLicense> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 辖区编号
     */
    private Long jurisdiction;

    /**
     * 授权类型 1：永久；0：临时
     */
    private Long type;

    /**
     * 授权开始时间（忽略null值的判断）
     *
     */
    @TableField(value = "start_time", strategy= FieldStrategy.IGNORED)
    private Long startTime;

    /**
     * 授权结束时间（忽略null值的判断）
     */
    @TableField(value = "end_time", strategy= FieldStrategy.IGNORED)
    private Long endTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String username;


}
