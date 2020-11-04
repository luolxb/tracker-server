package com.nts.iot.rest;

import com.nts.iot.dto.OverSpeedAlarmVo;
import com.nts.iot.service.FindElectronicFenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
/**
 * 查询指定辖区下所有的电子围栏的点
 */
public class FindElectronicFenceController {
    @Autowired
    private FindElectronicFenceService findElectronicFenceService;

    /**
     * 查询指定辖区下所有的电子围栏的点
     *
     * @param jurisdiction
     * @return
     */
    @RequestMapping("/findElectronicFenc/{jurisdiction}")
    public List<List<Map<String, Object>>>  FindElectronicFence(@PathVariable String jurisdiction) {
        return findElectronicFenceService.FindElectronicFence(jurisdiction);
    }

    /**
     * 超速报警
     * @param startTime
     * @param endTime
     * @param deviceNo
     * @return
     */
    @GetMapping("/overSpeedAlarm")
    public List<OverSpeedAlarmVo> findOverSpeedAlarmVoList(@RequestParam("startTime") Long startTime,
                                                           @RequestParam("endTime") Long endTime,
                                                           @RequestParam("deviceNo") String deviceNo){
        return findElectronicFenceService.findOverSpeedAlarmVoList(startTime,endTime,deviceNo);
    }
}
