package com.nts.iot.modules.system.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BorrowingReturningDto implements Serializable {

    private Long id;

    private String total;

    private String loanNumber;

    private String isRepay;
}
