package com.nts.iot.modules.system.model.vo;

import com.nts.iot.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@ApiModel("设备激活码")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceActivationCode {

    @ApiModelProperty("设备IMEI")
    @Excel(name = "device IMEI")
    private String deviceNo;

    @ApiModelProperty("激活码")
    @Excel(name = "activation code")
    private String activationCode;

}
