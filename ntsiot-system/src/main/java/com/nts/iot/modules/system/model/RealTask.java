package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.nts.iot.modules.miniApp.dto.TrajectoryDto;
import lombok.Data;

import java.util.List;

/**
 * @Author: zzm@rnstec.com
 * @Date: 2019/7/9 09:54
 * @Description: 实际已下发的任务列表
 */
@TableName("t_real_task")
@Data
public class RealTask extends Model<RealTask> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;

    /**
     * 任务模板ID
     */
    @TableId(value = "task_temp_id")
    private Long taskTempId;

    /**
     * 任务日期 yyyymmdd
     */
    private String yyyymmdd;

    /**
     * 任务完成状态（0:未完成; 1:已完成；2：超时；3：进行中）
     */
    @TableId(value = "status")
    private String status;

    /**
     * 任务开启时间
     */
    @TableId(value = "begin_time")
    private Long beginTime;

    /**
     * 任务结束时间
     */
    @TableId(value = "over_time")
    private Long overTime;


    /**
     * 系统巡更开始时间 yyyy-MM-dd HH:mm:ss
     */
    @TableField("sys_patrol_start_time")
    private String sysPatrolStartTime;
    /**
     * 系统巡更结束时间 yyyy-MM-dd HH:mm:ss
     */
    @TableField("sys_patrol_end_time")
    private String sysPatrolEndTime;

    /**
     * 巡更轨迹
     */
    private String path;

    /**
     * 是否启动（0:启动，1：关闭）
     */
    @TableField("start_up")
    private String startUp;

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

    /**
     * 是否审核 0：未审核； 1：已审核
     */
    @TableField("is_approval")
    private Long isApproval;

    /**
     * 车锁编号
     */
    @TableField("lock_barcode")
    private String lockBarcode;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    @TableField(exist = false)
    private List<RealCheckPoint> points;

    @TableField(exist = false)
    private TaskApproval taskApproval;

    /**
     * 巡更轨迹
     */
    @TableField(exist = false)
    private List<TrajectoryDto> trajectorys;

}
