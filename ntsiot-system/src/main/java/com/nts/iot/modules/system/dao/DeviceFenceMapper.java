package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.DeviceFence;
import com.nts.iot.modules.system.model.vo.DeviceFenceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface DeviceFenceMapper extends BaseMapper<DeviceFence> {

    List<DeviceFenceVo> getDeviceFenceVoByDeviceId(@Param("deviceIds") List<Long> deviceIds);
}
