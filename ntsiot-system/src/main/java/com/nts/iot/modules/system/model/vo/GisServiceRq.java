package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("GIS服务")
public class GisServiceRq {

    /**
     * GIS服务id
     */
    @ApiModelProperty("GIS服务id")
    private Long id;
    /**
     * gis服务名称
     */
    @NotBlank(message = "gis服务名称不能为空")
    @ApiModelProperty("gis服务名称")
    @Size(max = 20,message = "名称最大长度不能超过20个字符")
    private String name;
    /**
     * gis服务类型
     */
    @NotBlank(message = "gis服务类型不能为空")
    @ApiModelProperty("gis服务类型")
    private String type;
    /**
     * gis服务经度
     */
    @NotBlank(message = "gis服务经度不能为空")
    @ApiModelProperty("gis服务经度")
    private String latitude;

    /**
     * gis服务纬度
     */
    @NotBlank(message = "gis服务纬度不能为空")
    @ApiModelProperty("gis服务纬度")
    private String longitude;
    /**
     * gis服务备注
     */
    @ApiModelProperty("gis服务备注")
    @Size(max = 150,message = "备注最大长度不能超过150个字符")
    private String remark;

    /**
     * gis服务所属用户id
     */
    @NotNull(message = "gis服务所属用户id不能为空")
    @ApiModelProperty("gis服务所属用户id")
    private Long userId;
}
