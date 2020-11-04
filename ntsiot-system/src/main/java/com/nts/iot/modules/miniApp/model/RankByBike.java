package com.nts.iot.modules.miniApp.model;

import lombok.Data;

/**
 * @author:pengchenghe
 * @date: 2019-11-12
 * @time: 14:02
 */
@Data
public class RankByBike {
    private Long id;

    private String bikeBarcode;

    private String lockBarcode;

    private String logoUrl;

    private Double mile;
}
