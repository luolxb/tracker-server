package com.nts.iot.modules.system.dto;

import java.io.Serializable;
import java.util.List;

public class MissionPointTaskDto implements Serializable {
    private Long id;
    private String  title;
    private String  startTime;
    private String  endTime;
    // 是否完成
    private Boolean  completed;

    private String scope;
    // 考核指标
    private String target;

    // 图片urlList
    private List<String> sourceData;

    // 上传图片最大数量
    private String uploadImgNumber;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    // 音频urlList
    private String frequency;

    private String remark;

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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUploadImgNumber() {
        return uploadImgNumber;
    }

    public void setUploadImgNumber(String uploadImgNumber) {
        this.uploadImgNumber = uploadImgNumber;
    }

    public List<String> getSourceData() {
        return sourceData;
    }

    public void setSourceData(List<String> sourceData) {
        this.sourceData = sourceData;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
