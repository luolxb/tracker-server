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
 * @Description: 任务配置模板
 */
@TableName("t_task_template")
@Data
public class TaskTemplate extends Model<TaskTemplate> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    @TableField("task_name")
    private String taskName;


    /**
     * 巡更轨迹
     */
    private String path;


    /**
     * 辖区
     */
    @TableField("dept_id")
    private Long deptId;


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

    @TableField(exist = false)
    private String taskPointName;

    @TableField(exist = false)
    private List<TaskPoint> taskPoints;

}
