package com.nts.iot.service.impl;

import com.nts.iot.dto.CollectMessage;
import com.nts.iot.manage.FindBikeManager;
import com.nts.iot.service.FindBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindBikeServiceImpl implements FindBikeService {

    @Autowired
    private FindBikeManager findBikeManager;

    /**
     * 查询指定区域内的车辆
     *
     * @param point
     * @return
     */
    @Override
    public List<CollectMessage> getCollectMessageByPoint(String point) {
        return findBikeManager.getCollectMessageByPoint(point);
    }
}
