package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zzm@rnstec.com
 * @Date: 2019/7/9 10:06
 * @Description: 任务必到点
 */
@TableName("t_real_check_point")
@Data
public class RealCheckPoint extends Model<RealCheckPoint> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    @TableField("real_task_id")
    private Long taskId;

    /**
     * 必到点id
     */
    @TableField("point_id")
    private Long pointId;

    /* 必到点相关字段 */
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
     * sort
     */
    @TableField(value = "sort")
    private Long sort;


    /**
     * 范围
     */
    @TableField(value = "scope")
    private String scope;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 说明
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 辖区编号
     */
    @TableField(value = "jurisdiction")
    private String jurisdiction;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 更新者
     */
    @TableField(value = "updater")
    private String updater;

    /* 模板的开始和结束时间 */
    @TableField(exist = false)
    private String mainStartTime;

    @TableField(exist = false)
    private String mainEndTime;

}
