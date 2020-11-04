package com.nts.iot.modules.miniApp.model;

import lombok.Data;

import java.util.List;

/**
 * @author:pengchenghe
 * @date: 2019-11-15
 * @time: 11:38
 */
@Data
public class HistoryApiDto {
    private Double distance;

    private Long stopTime;

    /**
     * 轨迹
     */
    private List<Object> path;
}
