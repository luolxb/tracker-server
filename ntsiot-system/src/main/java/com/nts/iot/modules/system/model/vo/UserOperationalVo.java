package com.nts.iot.modules.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户运营统计 表现实体类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserOperationalVo {

    /**
     * 设备总数
     */
    private Integer deviceNum;

    /**
     * 服务到期
     */
    private Integer serviceExpires;

    /**
     * 今日激活
     */
    private Integer todayActivation;

    /**
     * 离线三天之内
     */
    private Integer offLine3Day;

    /**
     * 在线
     */
    private Integer onLine;

}
