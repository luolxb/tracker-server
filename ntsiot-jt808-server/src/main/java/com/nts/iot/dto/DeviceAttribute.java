package com.nts.iot.dto;

import lombok.Data;

/**
 * @program: ntsiot-jt808-server
 * @description: 查询终端属性
 * @author: wongshan
 * @create: 2019-08-02 18:35
 **/
@Data
public class DeviceAttribute {
    private String deviceNo;
    private String apn;//cmcc
    private String ipAddress;//网关IP地址
    private int port;//网关端口
    private int work_fre;//工作期间发送频率 单位：秒
    private int savemode_fre;//待机状态发送频率 单位：秒
}
