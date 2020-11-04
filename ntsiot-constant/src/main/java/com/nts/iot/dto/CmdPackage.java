package com.nts.iot.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 *  system's rest → jt8080's rest
 */
@Data
public class CmdPackage implements Serializable {

    public CmdPackage(String header, String deviceNo, String type, Map<String, Object> value, String timestamp) {
        this.header = header;
        this.deviceNo = deviceNo;
        this.type = type;
        this.value = value;
        this.timestamp = timestamp;
    }

    public CmdPackage() {
    }

    // 反控，查询参数等
    private String header;

    // 锁的设备号
    private String deviceNo ;

    // 开锁，关锁 等
    private String type;

    // 查询参数的值
    private Map<String, Object> value;

    private String timestamp;


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
