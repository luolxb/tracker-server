package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆运行(订单)统计
 */
@Data
public class OrderBikeQueryDTO implements Serializable {
    // 用户编号
    private String userId;
    // 用户姓名
    private String userName;
    //  订单编号list
    private List<Long> orderIds = new ArrayList<>();

}
