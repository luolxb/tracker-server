package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 4S店与客服
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("4S店与客服")
public class CustomerServiceVo {

    @ApiModelProperty("4S店与客服 id")
    private Long id;

    /**
     * b
     */
    @ApiModelProperty("救援电话")
    private String helpPhone;

    /**
     * 预约电话
     */
    @ApiModelProperty("预约电话")
    private String bookingLine;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
}
