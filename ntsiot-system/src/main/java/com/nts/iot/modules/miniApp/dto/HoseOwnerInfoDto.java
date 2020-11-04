package com.nts.iot.modules.miniApp.dto;

import java.io.Serializable;
import java.util.List;

public class HoseOwnerInfoDto implements Serializable {
    private String name ;
    private String phone ;
    private String address ;
    private String deptId ;
    private List<HoseTenantDto> tenantList ;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<HoseTenantDto> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<HoseTenantDto> tenantList) {
        this.tenantList = tenantList;
    }
}
