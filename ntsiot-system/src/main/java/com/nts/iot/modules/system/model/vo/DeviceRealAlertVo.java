package com.nts.iot.modules.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @PackageName: com.nts.iot.modules.system.model.vo
 * @program: ntsiot
 * @author: ruosen
 * @create: 2020-06-23 09:48
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceRealAlertVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备型号
     */
    private String deviceType;

    /**
     * 报警时间
     */
    private Date alertTime;
    /**
     * 设备状态
     */
    private Integer deviceStatus;


}
