package com.nts.iot.modules.monitor.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.sql.Timestamp;

/**
 * pv 与 ip 统计
 *
 * @author jie
 * @date 2018-12-13
 */
@Data
@TableName("visits")
public class Visits extends Model<Visits> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String date;

    @TableField("pv_counts")
    private Long pvCounts;

    @TableField("ip_counts")
    private Long ipCounts;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("week_day")
    private String weekDay;
}
