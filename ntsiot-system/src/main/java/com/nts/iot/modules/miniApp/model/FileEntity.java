package com.nts.iot.modules.miniApp.model;

import lombok.Data;

@Data
public class FileEntity {
    private String type;
    private String size;
    private String path;
    private String titleOrig;
    private String titleAlter;
    private Long uploadTime;
}
