package com.nts.iot.modules.system.dto;


import lombok.Data;

@Data
public class StaticsParam {

    // 开始时间
    private Long startTime ;
    // 结束时间
    private Long endTime;
    // 辖区id
    private String deptId;
}
