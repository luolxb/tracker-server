package com.nts.iot.service;

import com.nts.iot.dto.CollectMessage;

import java.util.List;

public interface FindBikeService {
    /**
     * @param point
     * @return
     */
    List<CollectMessage> getCollectMessageByPoint(String point);
}
