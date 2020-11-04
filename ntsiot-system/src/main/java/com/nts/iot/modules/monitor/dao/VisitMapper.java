package com.nts.iot.modules.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.monitor.domain.Visits;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisitMapper extends BaseMapper<Visits> {
}
