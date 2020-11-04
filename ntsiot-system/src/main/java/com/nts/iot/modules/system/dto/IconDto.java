package com.nts.iot.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IconDto implements Serializable {
    private String icon;
    private String name;
    private String longitude;
    private String latitude;
}
