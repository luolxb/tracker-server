package com.nts.iot.modules.system.model.vo;

import lombok.Data;

@Data
public class DeviceAlertTypeVo {

    private String deviceName;
    private String deviceNo;
    private String alertType;
    private Integer num;
}
