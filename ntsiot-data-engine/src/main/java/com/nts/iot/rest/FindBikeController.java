package com.nts.iot.rest;

import com.nts.iot.dto.CollectMessage;
import com.nts.iot.service.FindBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询附近自行车辆
 */
@RestController
@RequestMapping("")
public class FindBikeController {

    @Autowired
    private FindBikeService findBikeService;

    /**
     * 查询附近自行车辆
     *
     * @param point 点=经，纬
     * @return
     */
    @RequestMapping("/findBike/{point}")
    public List<CollectMessage> saveByDeviceNo(@PathVariable String point) {
        return findBikeService.getCollectMessageByPoint(point);
    }
}
