package com.nts.iot.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 车辆运行统计
 */
@Data
public class DeptBikeStaticDTO implements Serializable {
    // 辖区编号
    private String deptId;
    // 辖区名称
    private String deptName;
    // 总行驶里程
    private String totalDistance;
}
