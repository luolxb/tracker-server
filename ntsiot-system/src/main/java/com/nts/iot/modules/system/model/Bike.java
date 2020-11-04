package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author zhc@rnstec.com
 * @Description 车辆表
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */

@TableName("bike")
@Data
public class Bike extends Model<Bike> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 车辆条码
     */
    @TableField(value = "bike_barcode")
    private String bikeBarcode;

    /**
     * 智能锁条码
     */
    @TableField(value = "lock_barcode")
    private String lockBarcode;

    /**
     * 所属辖区
     */
    @TableField(value = "jurisdiction_id")
    private Long jurisdiction;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 鉴权码
     */
    @TableField(value = "register_no")
    private String registerNo;


    /**
     * sim卡iccid号
     */
    @TableField(value = "iccid")
    private String iccid;

    /**
     * 1-无需开锁；2-扫码开锁；
     */
    @TableField(value = "unlock_mode")
    private Integer unlockMode;

    /**
     * 车辆状态
     */
    private Long status;

    /**
     * 骑行人
     */
    private String user;

    /**
     * 骑行人电话
     */
    private String phone;

    /**
     * 车辆类型
     */
    private String type;

    /**
     * 图标
     */
    @TableField("bike_icon")
    private String bikeIcon;

    /**
     * 车辆类型
     */
    @TableField(exist = false)
    private String bikeType;


    /**
     * 纬度
     */
    @TableField(exist = false)
    private String latitude;

    /**
     * 经度
     */
    @TableField(exist = false)
    private String longitude;

    /**
     * 排序序号
     */
    @TableField("bike_seq")
    private Long bikeSeq;

    /**
     * 是否显示小尾巴
     */
    @TableField("show_real_line")
    private String showRealLine;

    /**
     * 设备最后上传数据时间
     */
    @TableField(exist = false)
    private String deviceEndTime;


    /**
     * 颜色
     */
    @TableField("color")
    private String color;


}
