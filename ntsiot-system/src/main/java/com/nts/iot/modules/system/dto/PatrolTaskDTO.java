package com.nts.iot.modules.system.dto;

import com.nts.iot.modules.system.model.RealCheckPoint;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/10 14:16
 * @Description:
 */
@Data
public class PatrolTaskDTO implements Serializable {

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务
     */
    private String taskName;

    /**
     * 巡更人
     */
    private String patrolman;

    /**
     * 巡更时间
     */
    private Long beginTime;

    /**
     * 巡更时间
     */
    private Long overTime;

    /**
     * 分数
     */
    private Long score;

    /**
     * 状态
     */
    private String status;

    /**
     * 系统巡更开始时间
     */
    private String sysPatrolStartTime;
    /**
     * 系统巡更结束时间
     */
    private String sysPatrolEndTime;

    private List<RealCheckPoint> points;

    /**
     * 是否审核 0：未审核； 1：已审核
     */
    private String isApproval;
}
