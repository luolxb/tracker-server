package com.nts.iot.modules.system.dto;

import java.io.Serializable;
import java.util.List;

public class MissionDto implements Serializable {

    private Long id;
    // 任务名称
    private String  title;
    // 开始时间
    private String  startTime;
    // 结束时间
    private String  endTime;
    // 是否开启
    private Boolean  started;
    // 必到点list
    private List<MissionPointDto> points ;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public List<MissionPointDto> getPoints() {
        return points;
    }

    public void setPoints(List<MissionPointDto> points) {
        this.points = points;
    }
}
