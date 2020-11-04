package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BikeTrajectoryDto implements Serializable {
    /**
     * 车锁编号
     */
    private String lockNo;

    /**
     * 车辆编号
     */
    private String bikeNo;

    private List<TrajectoryDto> trajectoryDtoList;
}
