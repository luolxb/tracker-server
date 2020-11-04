package com.nts.iot.service;

import com.nts.iot.dto.OverSpeedAlarmVo;

import java.util.List;
import java.util.Map;

public interface FindElectronicFenceService {
    /**
     * 查询指定辖区下所有的电子围栏的点
     *
     * @param jurisdiction 辖区编号
     * @return
     */
    List<List<Map<String, Object>>> FindElectronicFence(String jurisdiction);

    List<OverSpeedAlarmVo> findOverSpeedAlarmVoList(Long startTime, Long endTime, String deviceNo);
}
