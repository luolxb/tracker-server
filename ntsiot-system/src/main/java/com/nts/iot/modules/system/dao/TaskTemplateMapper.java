package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.PatrolTaskDTO;
import com.nts.iot.modules.system.model.TaskTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/9 10:09
 * @Description:
 */
@Mapper
@Repository
public interface TaskTemplateMapper extends BaseMapper<TaskTemplate> {

    /**
     * 列表
     * @param page
     * @param title
     * @param jurisdictions
     * @return
     */
    Page<TaskTemplate> selectByPage(Page<TaskTemplate> page, @Param("title") String title, @Param("jurisdictions") List<String> jurisdictions);


    /**
     * 更改任务状态
     * @param taskId
     * @param status
     */
    void updateTaskStatus(@Param("taskId") Long taskId, @Param("status") String status);

    /**
     * 列表
     * @param page
     * @param title
     * @param jurisdictions
     * @return
     */
    Page<PatrolTaskDTO> selectTaskApprovalsByPage(Page<PatrolTaskDTO> page, @Param("title") String title, @Param("patrolman") String patrolman,
                                                  @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdictions") List<String> jurisdictions);

}
