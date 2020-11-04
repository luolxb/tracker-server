package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nts.iot.modules.system.model.DeviceRealAlert;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceRealAlertVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.nts.iot.modules.system.dao
 * @program: ntsiot
 * @author: ruosen
 * @create: 2020-06-22 18:32
 **/

@Repository
@Mapper
public interface DeviceRealAlertMapper extends BaseMapper<DeviceRealAlert> {

    Map<String, Integer> proportion(User user);


    List<DeviceRealAlertVo> proportionList(IPage<DeviceRealAlertVo> page,
                                           @Param("userId") Long userId,
                                           @Param("alertType") String alertType);
}
