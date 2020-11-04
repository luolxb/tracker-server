package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/9 10:01
 * @Description: 巡更人员表
 */
@TableName("t_task_patrolman")
@Data
public class TaskPatrolman extends Model<TaskPatrolman> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务id
     */
    @TableField("task_id")
    private Long taskId;

    /**
     * 巡更人员id
     */
    @TableField("patrolman_id")
    private Long patrolmanId;

    /**
     * 巡更人员名称
     */
    private String patrolman;

    /**
     * 电话
     */
    private String phone;

    /**
     * 辖区
     */
    @TableField("dept_id")
    private Long deptId;
}
