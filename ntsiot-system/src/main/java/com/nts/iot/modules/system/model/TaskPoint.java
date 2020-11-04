package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/9 10:06
 * @Description: 任务必到点
 */
@TableName("t_task_point")
@Data
public class TaskPoint extends Model<TaskPoint> {

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
     * 必到点id
     */
    @TableField("point_id")
    private Long pointId;

    /**
     * 必到点名称
     */
    @TableField("point_name")
    private String pointName;

    /**
     * 必到点描述
     */
    @TableField("point_remark")
    private String pointRemark;


    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;
}
