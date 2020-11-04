package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/31 14:42
 * @Description:
 */
@Data
public class IllegalStatisticsDTO implements Serializable {

    private String time;
    private String type;
    private String cnt;
    private String avgTime;
    private String maxDealTime;
    private String minDealTime;


}
