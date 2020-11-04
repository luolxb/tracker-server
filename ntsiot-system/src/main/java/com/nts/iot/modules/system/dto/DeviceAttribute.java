package com.nts.iot.modules.system.dto;

import lombok.Data;

/**
 * @program: cyoubike-jt808-server
 * @description: 查询终端属性
 * @author: wongshan
 * @create: 2019-08-02 18:35
 **/
@Data
public class DeviceAttribute {
    private String deviceNo;
    private String apn;
    private String ipAddress;
    private int port;
    private int work_fre;
    private int savemode_fre;
}
