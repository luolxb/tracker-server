package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * 必到点
 */
@Data
@TableName("t_check_point")
public class CheckPoint extends Model<CheckPoint> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 范围
     */
    @TableField(value = "scope")
    private String scope;

    /**
     * 任务list
     */
    @TableField(exist = false)
    private String checkPointTaskList;

    /**
     * 任务list
     */
    @TableField(exist = false)
    private List<CheckPointTask> pointTaskList;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 删除任务id
     */
    @TableField(exist = false)
    private String taskId;


}
