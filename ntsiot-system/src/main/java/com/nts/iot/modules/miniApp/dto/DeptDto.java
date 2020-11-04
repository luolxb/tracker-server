package com.nts.iot.modules.miniApp.dto;

import java.io.Serializable;

public class DeptDto implements Serializable {
    // 辖区ID
    private Long id;
    // 车辆图标
    private String bikeIcon;
    // 辖区LOGO
    private String logo;
    // 辖区名称
    private String name;
    // 辖区 latitude
    private String latitude;
    // 辖区 longitude
    private String longitude;
    // 辖区电话
    private String deptPhone;
    // 市公安局名称
    private String pName;
    // 市公安局电话
    private String cityPhone;
    // 查询开始时间
    private Long startTime;
    // 查询结束时间
    private Long endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBikeIcon() {
        return bikeIcon;
    }

    public void setBikeIcon(String bikeIcon) {
        this.bikeIcon = bikeIcon;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCityPhone() {
        return cityPhone;
    }

    public void setCityPhone(String cityPhone) {
        this.cityPhone = cityPhone;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
