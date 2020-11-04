package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.TaskApproval;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface TaskApprovalService extends IService<TaskApproval> {

    /**
     * 保存审核信息
     * @param taskApproval
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer saveApproval(TaskApproval taskApproval);

    /**
     * 根据任务id 查找审核信息
     * @param taskId
     * @return
     */
    TaskApproval getApprovalByTaskId(Long taskId);
}
