package com.nts.iot.modules.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @PackageName: com.nts.iot.modules.system.model.vo
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-08 13:17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class DeviceAlertProportionVo {

    /**
     *  与new_dict 表的 alert_type_01类型对应
     */
    private Integer alert_type_01;
    private Integer alert_type_001;
    private Integer alert_type_002;
    private Integer alert_type_003;
    private Integer alert_type_004;
    private Integer alert_type_005;
    private Integer alert_type_006;
    private Integer alert_type_007;
    private Integer alert_type_008;
    private Integer alert_type_009;
    private Integer alert_type_0010;
    private Integer alert_type_0011;
    private Integer alert_type_0012;
}
