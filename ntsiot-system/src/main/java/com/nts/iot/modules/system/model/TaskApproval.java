package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/10 13:58
 * @Description: 巡更任务审核
 */
@TableName("t_task_approval")
@Data
public class TaskApproval {
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
     * 分数
     */
    private Long score;

    /**
     * 描述
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;
}
