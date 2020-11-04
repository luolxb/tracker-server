package com.nts.iot.modules.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.model.LogMybatis;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LogMapper extends BaseMapper<LogMybatis> {

    List<LogMybatis> queryAll();
}
