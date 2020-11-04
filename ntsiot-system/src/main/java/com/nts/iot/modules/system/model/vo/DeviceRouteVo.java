package com.nts.iot.modules.system.model.vo;

import com.nts.iot.modules.miniApp.dto.Road;
import com.nts.iot.modules.miniApp.dto.Trajectory;
import com.nts.iot.modules.system.model.CheckPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel("设备路径")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceRouteVo extends DeviceVo{

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String latitude;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String longitude;

    /**
     * 时间,YY-MM-DD- HH:mm:ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）
     */
    @ApiModelProperty("时间,YY-MM-DD- HH:mm:ss（GMT+8时间，本标准中之后涉及的时间均采用此时区）")
    private String time;

    /**
     * 毫秒
     */
    @ApiModelProperty("毫秒")
    private Long mis;

    /**
     * 速度,1/10km/h
     */
    @ApiModelProperty("速度,1/10km/h")
    private String speed;

    /**
     * 方向,0～359，正北为0，顺时针
     */
    @ApiModelProperty("方向,0～359，正北为0，顺时针")
    private String course;

}
