package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nts.iot.modules.system.model.DeviceAlert;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceAlertProportionVo;
import com.nts.iot.modules.system.model.vo.DeviceAlertStatisticsVo;
import com.nts.iot.modules.system.model.vo.DeviceAlertTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.nts.iot.modules.system.dao
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-08 13:12
 **/
@Repository
@Mapper
public interface DeviceAlertMapper extends BaseMapper<DeviceAlert> {

    Map<String, Integer> proportion(User user);

    List<Map<String, Object>> monitor(@Param("user") User user,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);

    List<DeviceAlert> selectDeviceAlertPage(IPage<DeviceAlert> page,
                                            @Param("status") Integer status,
                                            @Param("userId") Long userId,
                                            @Param("deviceNo") String deviceNo,
                                            @Param("startDate") String startDate,
                                            @Param("endDate") String endDate);

    List<DeviceAlertTypeVo> statistics(@Param("userId") Long userId,
                                       @Param("deviceNo") String deviceNo,
                                       @Param("startDate") String startDate,
                                       @Param("endDate") String endDate);
}
