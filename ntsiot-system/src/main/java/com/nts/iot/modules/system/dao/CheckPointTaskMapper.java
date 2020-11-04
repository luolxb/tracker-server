package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.CheckPointTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CheckPointTaskMapper extends BaseMapper<CheckPointTask> {
    /**
     * 根据必到点id查询任务
     *
     * @param id 必到点id
     * @return
     */
    List<CheckPointTask> queryAllById(@Param("id") Long id);

    /**
     * 根据必到点id删除任务
     *
     * @param id 必到点id
     */
    void deleteTaskById(@Param("id") Long id);
}
