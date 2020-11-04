package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 里程统计
 */
@ApiModel("里程统计")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MileageStatisticsVo {

    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String deviceNo;

    /**
     * 里程(公里)
     */
    @ApiModelProperty("里程(公里)")
    private Double mileage;

    /**
     * 超速(次)
     */
    @ApiModelProperty("超速(次)")
    private Integer speed;

    /**
     * 1
     */
    @ApiModelProperty("停留(次)")
    private Integer stop;

    /**
     * 围栏内里程
     */
    @ApiModelProperty("围栏内里程")
    private Double fenceIn;

    /**
     * 围栏外里程
     */
    @ApiModelProperty("围栏外里程")
    private Double fenceOut;


}
