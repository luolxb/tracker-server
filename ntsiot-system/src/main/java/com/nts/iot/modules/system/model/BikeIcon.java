package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/30 15:18
 * @Description:
 */
@TableName("bike_icon")
@Data
public class BikeIcon extends Model<BikeIcon> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 辖区
     */
    private Long dept;

    /**
     * 车辆类型
     */
    @TableField("bike_type")
    private String bikeType;

    /**
     * 图标
     */
    @TableField("bike_icon")
    private String bikeIcon;

    /**
     * 描述
     */
    private String remark;

    /**
     * 是否显示小尾巴
     */
    @TableField("show_real_line")
    private String showRealLine;

    /**
     * 小尾巴颜色
     */
    @TableField("color")
    private String color;
}
