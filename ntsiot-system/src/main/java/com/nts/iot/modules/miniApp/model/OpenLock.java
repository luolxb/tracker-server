package com.nts.iot.modules.miniApp.model;

import lombok.Data;

/**
 * @author:pengchenghe
 * @date: 2019-11-20
 * @time: 15:57
 */
@Data
public class OpenLock {
    private  String message;
    private String deviceNo;
    private String openId;
    private String bikeBarCode;
}
