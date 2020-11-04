package com.nts.iot.modules.miniApp.dto;

import java.io.Serializable;

public class HoseTenantDto implements Serializable {
    private String name ;
    private String phone ;
    private String idCard ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
