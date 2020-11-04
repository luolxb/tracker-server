package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
/**
 * 视频表
 * @author 蜜瓜
 * 2019年11月12日
 */
@TableName("t_video")
@Data
public class Video extends Model<Video>{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 视频标题
     */
   
    private String title;
    /**
     * 视频地址
     */
   
    private String url;
    /**
     * 辖区ID
     */
    @TableField(value = "dept_id")
    private Long deptId;
    /**
     * 辖区名称
     */
    @TableField(value = "dept_name")
    private String deptName;
    /**
     * 有效起始时间
     */
    @TableField(value = "start_time")
    private Long startTime;
    /**
     * 有效结束时间
     */
    @TableField(value = "end_time")
    private Long endTime;
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
}
