package com.nts.iot.modules.system.model.vo;

import com.nts.iot.dto.CollectMessage;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@ApiModel("路径")
@Accessors(chain = true)
public class RoadVo {


    /**
     * 起始点
     */
    private CollectMessage from;

    /**
     * 到达点
     */
    private CollectMessage to;

    /**
     * 距离
     */
    private Double distance;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 起始点时间
     */
    private String beginTime;

    /**
     * 结束点时间
     */
    private String endTime;
}
