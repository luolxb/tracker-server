package com.nts.iot.jt808.protocol;

import lombok.Data;

/**
 * @program: ntsiot-jt808-server
 * @description: 终端属性
 * @author: wongshan
 * @create: 2019-08-07 14:22
 **/
@Data
public class DeviceAttribute {

    private String deviceNo;
    private String type;
    private String APN;
    private String IPAddress;
    private int port;
    private int work_fre;
    private int savemode_fre;
}
