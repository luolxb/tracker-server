package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.TaskInstance;
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
public interface TaskInstanceMapper extends BaseMapper<TaskInstance> {

    /**
     * 列表
     * @param page
     * @param title
     * @param jurisdictions
     * @return
     */
    Page<TaskInstance> selectByPage(Page<TaskInstance> page, @Param("title") String title, @Param("jurisdictions") List<String> jurisdictions);


    /**
     * 更改任务状态
     * @param taskId
     * @param status
     */
    void updateTaskStatus(@Param("taskId") Long taskId, @Param("status") String status);

    List<TaskInstance> getTimeInList();


}
