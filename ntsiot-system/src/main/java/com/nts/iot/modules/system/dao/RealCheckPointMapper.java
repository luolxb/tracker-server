package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.dto.TaskDetailDto;
import com.nts.iot.modules.system.model.RealCheckPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RealCheckPointMapper extends BaseMapper<RealCheckPoint> {

    /**
     * 查询任务详细
     * @param id
     * @return
     */
    List<TaskDetailDto> selectTaskDetail(@Param("id") Long id);
}
