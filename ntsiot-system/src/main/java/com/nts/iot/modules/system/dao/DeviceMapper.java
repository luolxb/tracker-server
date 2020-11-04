package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Device;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceVo;
import com.nts.iot.modules.system.model.vo.UserOperationalVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.dao
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:10
 **/
@Repository
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    DeviceVo selectById1(@Param("id") Long id);

    /**
     * 获取分页信息
     *
     * @param page
     * @param search
     * @return
     */
    List<DeviceVo> queryPage(IPage<DeviceVo> page,
                             @Param("userId") Long userId,
                             @Param("search") String search,
                             @Param("userExpiresTimeStart") String userExpiresTimeStart,
                             @Param("userExpiresTimeEnd") String userExpiresTimeEnd,
                             @Param("platformExpiresTimeStart") String platformExpiresTimeStart,
                             @Param("platformExpiresTimeEnd") String platformExpiresTimeEnd);

    int updateDevice(Device device);

    /**
     * 运营统计
     *
     * @param userId
     * @param date
     * @return
     */
    UserOperationalVo operationalStatistics(@Param("userId") Long userId,
                                            @Param("date") String date);

    /**
     * 获取离线设备
     *
     * @param page
     * @param userId
     * @param search
     * @param startDate
     * @param endDate
     * @return
     */
    List<DeviceVo> queryPageOffLine(Page<DeviceVo> page,
                                    @Param("userId") Long userId,
                                    @Param("search") String search,
                                    @Param("startDate") String startDate,
                                    @Param("endDate") String endDate);

        DeviceVo queryByDeviceId(@Param("deviceId") Long deviceId);


        DeviceVo queryByDeviceNo(@Param("queryByDevice_no") String queryByDeviceNo);

        List<DeviceVo> queryListByFenceId(@Param("fenceId") Long fenceId);

    List<DeviceVo> queryPageExpires(IPage<DeviceVo> page,
                             @Param("userId") Long userId,
                             @Param("search") String search,
                             @Param("userExpiresTimeStart") String userExpiresTimeStart,
                             @Param("userExpiresTimeEnd") String userExpiresTimeEnd,
                             @Param("platformExpiresTimeStart") String platformExpiresTimeStart,
                             @Param("platformExpiresTimeEnd") String platformExpiresTimeEnd);

}
