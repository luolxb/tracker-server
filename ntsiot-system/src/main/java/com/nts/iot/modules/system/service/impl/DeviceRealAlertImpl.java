package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.system.dao.DeviceRealAlertMapper;
import com.nts.iot.modules.system.model.DeviceRealAlert;
import com.nts.iot.modules.system.model.vo.DeviceRealAlertVo;
import com.nts.iot.modules.system.service.DeviceRealAlertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.service.impl
 * @program: ntsiot
 * @author: ruosen
 * @create: 2020-06-22 18:36
 **/

@Slf4j
@Service
public class DeviceRealAlertImpl extends ServiceImpl<DeviceRealAlertMapper, DeviceRealAlert> implements DeviceRealAlertService {


    @Override
    @Transactional(readOnly = true)
    public Page<DeviceRealAlertVo> proportionList(Long userId, String alertType, Pageable pageable) {
        Page<DeviceRealAlertVo> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        List<DeviceRealAlertVo> deviceRealAlertVos = this.baseMapper.proportionList(page, userId, alertType);
        page.setRecords(deviceRealAlertVos);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delDeviceRealAlert(CollectMessage message) {
        // 判断 device_real_alert是否存在数据
        // 判断 device_real_alert 是否存在数据
        QueryWrapper<DeviceRealAlert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_no", message.getDeviceNo());
        queryWrapper.eq("alert_type", "alert_type_002");

        List<DeviceRealAlert> list = this.list(queryWrapper);
        // 如果存在 删除
        // 如果不存在 不操作
        if (!CollectionUtils.isEmpty(list)) {
            try {
                boolean remove = this.remove(queryWrapper);
                if (!remove) {
                    log.error("删除实时报警失败");
                }
            } catch (Exception e) {
                log.error("删除实时报警失败", e);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDeviceRealAlert(CollectMessage message) {
       // 判断 device_real_alert 是否存在数据
        QueryWrapper<DeviceRealAlert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_no", message.getDeviceNo());
        queryWrapper.eq("alert_type", "alert_type_002");

        List<DeviceRealAlert> list = this.list(queryWrapper);
        // 如果存在数据，不做操作
        // 如果不存在数据，新增操作
        if (CollectionUtils.isEmpty(list)) {
            DeviceRealAlert deviceRealAlert = new DeviceRealAlert();
            BeanUtils.copyProperties(message, deviceRealAlert);
            // alert_type_002 超速报警
            deviceRealAlert.setAlertType("alert_type_002");
            deviceRealAlert.setCreateTime(new Date());
            deviceRealAlert.setCreateBy("admin");
            try {
                boolean save = this.save(deviceRealAlert);
                if (!save) {
                    log.error("新增实时报警失败");
                }
            } catch (Exception e) {
                log.error("新增实时报警失败", e);
            }
        }
    }
}
