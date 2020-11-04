package com.nts.iot.modules.system.dto;

import java.io.Serializable;
import java.util.List;

public class MissionPointDto implements Serializable {
    // id
    private Long id;
    // 标题
    private String  title;
    // 纬度
    private String latitude;
    // 经度
    private String longitude;

    private Boolean isOver;

    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    private List<MissionPointTaskDto> subMission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Boolean getIsOver() {
        return isOver;
    }

    public void setIsOver(Boolean over) {
        isOver = over;
    }

    public List<MissionPointTaskDto> getSubMission() {
        return subMission;
    }

    public void setSubMission(List<MissionPointTaskDto> subMission) {
        this.subMission = subMission;
    }
}
