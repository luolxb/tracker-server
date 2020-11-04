package com.nts.iot.modules.miniApp.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/31 15:16
 * @Description:
 */
@Data
public class Serie {
    private String name;

    private String type;

    private String stack;

    private String barWidth;

    private List<String> data;
}
