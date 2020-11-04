package com.nts.iot.service;

import com.nts.iot.entity.Security;

import java.util.List;
import java.util.Optional;

public interface StatisticsBikeInfoService {
    /**
     * 统计车辆信息
     */
    List<Security> statisticsBikeInfo();

    /**
     * 通过辖区查询Security
     *
     * @param jurisdiction
     * @return
     */
    Security findAllByJurisdiction(String jurisdiction);
}
