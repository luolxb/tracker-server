package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author zhc@rnstec.com
 * @Description 虚拟桩
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */

@TableName("virtual_pile")
@Data
public class VirtualPile extends Model<VirtualPile> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 名称
     */
    private String name;

    /**
     * 范围
     */
    private Long scope;

    /**
     * 说明
     */
    private String remark;

    /**
     * 辖区编号
     */
    private Long jurisdiction;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 辖区名称
     */
    @TableField(exist = false)
    private String deptName;
}
