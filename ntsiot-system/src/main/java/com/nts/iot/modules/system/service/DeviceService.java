package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.DeviceStateQueryDTO;
import com.nts.iot.modules.system.dto.DeviceStatisticDto;
import com.nts.iot.modules.system.model.Device;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.*;
import com.nts.iot.util.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.service
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:13
 **/
public interface DeviceService extends IService<Device> {

    void create(DeviceRq deviceRq, User user);

    Page<DeviceVo> queryPage(String search,
                             String userExpiresTimeStart,
                             String userExpiresTimeEnd,
                             String platformExpiresTimeStart,
                             String platformExpiresTimeEnd,
                             Pageable pageable,
                             Long userId);

    RestResponse export(String search,
                        String userExpiresTimeStart,
                        String userExpiresTimeEnd,
                        String platformExpiresTimeStart,
                        String platformExpiresTimeEnd,
                        Long userId);

    DeviceVo detail(Long id);

    void update(DeviceRq deviceRq, User user);

    void sell(List<DeviceRq> deviceRqList, User user);

    void resetPass(User user, Long id);

    void updateType(List<DeviceRq> deviceRqList, User user);

    void delete(List<Long> ids, User user);

    UserOperationalVo operationalStatistics(Long userId);

    void extension(List<DeviceRq> deviceRqList, User user);

    void userExtension(List<DeviceRq> deviceRqList, User user);

    Page<DeviceVo> queryPageOffLine(String search, String startDate, String endDate, Pageable pageable, Long userId);

    Integer importDevice(MultipartFile file, Long userId, User user) throws Exception;

    Integer importActivationCode(MultipartFile file, Long userId, User user) throws Exception;

    /**
     * 设备列表（redis初始化）
     *
     * @return
     */
    void initDevices();

    void updateDeviceLocation(Device device);

    /**
     * 设备的位置
     *
     * @param userId
     * @return
     */
    List<DeviceLocationInfoVo> deviceLocation(String search, Long userId);

    DeviceMonitorVo queryAll(String search, Long userId);

    List<DeviceRouteVo> deviceHistoricalRoute(Long deviceId, Long startTime, Long endTime);

    List<DeviceLocationInfoVo> monitor(Long deviceId);


    List<String> queryPageDeviceNo(String search,Long userId);

    List<DeviceStatisticDto> findDeviceStatistic(Long userId,Long startTime,Long endTime,String deviceNo);

    List<DeviceVo> queryAllByUserId(Long userId, User user, String deviceNo);

    List<DeviceVo> queryListByFenceId(Long fenceId);

    Page<DeviceVo> queryPageExpires(String search, String userExpiresTimeStart, String userExpiresTimeEnd, String platformExpiresTimeStart, String platformExpiresTimeEnd, Pageable pageable, Long userId);

    List<DeviceRouteVo> deviceStatisticRoute(Long userId, String deviceNo, Long startTime, Long endTime);

    List<OverSpeedAlarmVo> overSpeedAlarm(Long userId, Long startTime, Long endTime, String deviceNo);

}
