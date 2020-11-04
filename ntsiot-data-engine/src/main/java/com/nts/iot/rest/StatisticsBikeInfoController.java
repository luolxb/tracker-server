package com.nts.iot.rest;

import com.nts.iot.entity.Security;
import com.nts.iot.service.StatisticsBikeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询前一天指定辖区下所有车辆的订单信息
 */
@RestController
@RequestMapping("")
public class StatisticsBikeInfoController {

    @Autowired
    private StatisticsBikeInfoService statisticsBikeInfoService;

    /**
     * 查询前一天指定辖区下所有车辆的订单信息
     *
     * @param jurisdiction 辖区编号
     * @return
     */
    @RequestMapping("/findSecurity/{jurisdiction}")
    public Security findSecurity(@PathVariable String jurisdiction) {
        return statisticsBikeInfoService.findAllByJurisdiction(jurisdiction);
    }

}
