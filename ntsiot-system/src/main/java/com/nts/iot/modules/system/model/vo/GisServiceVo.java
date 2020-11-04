package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("GIS服务")
public class GisServiceVo {

    /**
     * GIS服务id
     */
    @ApiModelProperty("GIS服务id")
    private Long id;
    /**
     * gis服务名称
     */
    @ApiModelProperty("gis服务名称")
    private String name;
    /**
     * gis服务类型
     */
    @ApiModelProperty("gis服务类型")
    private String type;
    /**
     * gis服务经度
     */
    @ApiModelProperty("gis服务经度")
    private String latitude;

    /**
     * gis服务纬度
     */
    @ApiModelProperty("gis服务纬度")
    private String longitude;
    /**
     * gis服务备注
     */
    @ApiModelProperty("gis服务备注")
    private String remark;

    /**
     * gis服务所属用户id
     */
    @ApiModelProperty("gis服务所属用户id")
    private Long userId;
}
