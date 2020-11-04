package com.nts.iot.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: ntsiot-jt808-server
 * @description: ota升级package
 * @author: wongshan
 * @create: 2019-07-31 10:16
 **/
@Data
public class OtaPackage {
    private List<String> deviceNos;
    private int type;
    private String url;
    private int port;
    private String sw_version;
}
