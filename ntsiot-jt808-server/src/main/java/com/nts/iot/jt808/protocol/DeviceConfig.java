package com.nts.iot.jt808.protocol;

import lombok.Data;

/**
 * @program: ntsiot-jt808-server
 * @description: 设备配置参数
 * @author: wongshan
 * @create: 2019-08-02 17:00
 **/
@Data
public class DeviceConfig {
    private String APN;
    private String IPv4Address;
    private int port;
    //工作模式数据点汇报频率
    private int NormalUploadFrequency;
    //休眠模式数据点汇报频率
    private int SleepUploadFrequency;
}
