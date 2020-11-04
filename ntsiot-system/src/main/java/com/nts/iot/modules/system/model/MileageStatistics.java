package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@TableName("mileage_statistics")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MileageStatistics {

    /**
     * 理财统计ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备编号
     */
    @TableField("device_no")
    private String deviceNo;

    /**
     * 里程(公里)
     */
    private Double mileage;

    /**
     * 超速(次)
     */
    private Integer speed;

    /**
     * 停留(次)
     */
    private Integer stop;

    /**
     * 围栏内里程
     */
    @TableField("fence_in")
    private Double fenceIn;

    /**
     * 围栏外里程
     */
    @TableField("fence_out")
    private Double fenceOut;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_by")
    private String createBy;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_by")
    private String updateBy;

    /**
     * 删除标记  1:正常  2：删除
     */
    @TableField("del_flag")
    private Integer delFlag;
}
