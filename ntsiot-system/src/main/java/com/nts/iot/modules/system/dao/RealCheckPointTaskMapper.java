package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.RealCheckPointTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RealCheckPointTaskMapper extends BaseMapper<RealCheckPointTask> {
    List<RealCheckPointTask> queryAll();

}
