package com.nts.iot.modules.system.model.vo;


import io.swagger.annotations.Api;
import lombok.Data;

@Api
@Data
public class DeviceFenceVo {

    private String deviceNo;
    private String fenceName;

    private Long deviceId;
    private Long fenceId;
}
