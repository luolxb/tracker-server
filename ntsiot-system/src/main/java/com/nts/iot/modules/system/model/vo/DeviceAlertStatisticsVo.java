package com.nts.iot.modules.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class DeviceAlertStatisticsVo extends DeviceAlertProportionVo {
    private String deviceNo;
    private String deviceName;
}
