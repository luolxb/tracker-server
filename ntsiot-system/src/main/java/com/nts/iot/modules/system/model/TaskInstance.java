package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/9 09:54
 * @Description:  任务实例
 */
@TableName("t_task_instance")
@Data
public class TaskInstance extends Model<TaskInstance> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务实例名称
     */
    @TableField("task_name")
    private String taskName;

    /**
     * 任务模板名称
     */
    @TableField("template_name")
    private String templateName;


    /**
     * 系统巡更开始时间
     */
    @TableField("sys_patrol_start_time")
    private String sysPatrolStartTime;

    /**
     * 系统巡更结束时间
     */
    @TableField("sys_patrol_end_time")
    private String sysPatrolEndTime;


    /**
     * 是否启动（0:启动，1：关闭）
     */
    @TableField("start_up")
    private String startUp;

    /**
     * 辖区
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 模板ID
     */
    @TableId(value = "template_id")
    private Long templateId;

    /**
     * 重复类型
     */
    @TableId(value = "repeat_time")
    private String repeatTime;


    @TableField(exist = false)
    private List<TaskPatrolman> taskPatrolmen;


    @TableField(exist = false)
    private List<TaskPoint> taskPoints;

    /**
     * 下发执行时间
     */
    @TableField("exec_time")
    private Long execTime;


    /**
     * 生效日期
     */
    @TableField("active_date_start")
    private String activeDateStart;

    /**
     * 失效日期
     */
    @TableField("active_date_end")
    private String activeDateEnd;


}
