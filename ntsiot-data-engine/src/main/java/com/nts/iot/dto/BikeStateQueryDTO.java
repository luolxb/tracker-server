package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆运行(订单)统计
 */
@Data
public class BikeStateQueryDTO implements Serializable {
    private Long startTime;
    private Long endTime;
    //  订单编号list
    private List<String> Ids = new ArrayList<>();

}
