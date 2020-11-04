package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
* @author jie
* @date 2019-03-29
*/
@Data
@TableName("job")
@ApiModel("工作任务")
public class Job extends Model<Job> {

    /**
     * ID
     */
    @ApiModelProperty("工作任务ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序")
    private Long sort;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Boolean enabled;

    /**
     * 创建日期
     */
    @TableField("create_time")
    private Timestamp createTime;

    @TableField("dept_id")
    private Long deptId;

    @TableField(exist = false)
    private Dept dept;

}