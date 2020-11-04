package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zzm@rnstec.com
 * @Date: 2019/7/9 10:06
 * @Description: 必到点下的子任务
 */
@Data
@TableName("t_real_check_point_task")
public class RealCheckPointTask extends Model<RealCheckPointTask> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 必到点编号
     */
    @TableField(value = "real_point_id")
    private Long realPointId;

    /**
     * 子任务名称
     */
    @TableField(value = "task_name")
    private String name;

    /**
     * 是否完成任务
     */
    @TableField(value = "is_over")
    private Boolean isOver;

    /**
     *  考核指标（类型： 0:停留时间,1:上传图片,2:打卡签到）
     */
    @TableField(value = "target")
    private String target;

    /**
     * 说明
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Long creator;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 更新者
     */
    @TableField(value = "updater")
    private Long updater;


    /**
     * 停留开始时间
     */
    @TableField(value = "start_time")
    private String startTime;

    /**
     * 停留结束时间
     */
    @TableField(value = "end_time")
    private String endTime;

    /**
     * 是否根据停留时间执行
     */
    @TableField(value = "is_perform")
    private String isPerform;

    /**
     * 上传图片最大数
     */
    @TableField(value = "upload_img_number")
    private String uploadImgNumber;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 任务状态
     */
    @TableField(value = "task_state")
    private String taskState;
}
