package com.nts.iot.modules.system.dto;

import com.nts.iot.modules.miniApp.model.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskDetailDto implements Serializable {

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 必到点名称
     */
    private String name;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 说明
     */
    private String remark;

//    /**
//     *  字典表 类型名称
//     */
//    private String label;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 是否完成
     */
    private Boolean isOver;

    /**
     * 任务状态
     */
    private String taskState;

    /**
     * 任务状态
     */
    private List<Picture> pictureList;

    /**
     * 必到点经度
     */
    private String pLongitude;

    /**
     * 必到点纬度
     */
    private String pLatitude;

}
