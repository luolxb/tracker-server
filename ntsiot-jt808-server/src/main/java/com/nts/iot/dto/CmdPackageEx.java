package com.nts.iot.dto;

import lombok.Data;

import java.util.Map;

/**
 * 扩展的命令
 */
@Data
public class CmdPackageEx {
    private String header;
    private String deviceNo;
    private String type;
//    private String value;
    private Map<String,Object> value;
    private String timestamp;

}
