package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
/**
 * 小游戏表
 * @author 蜜瓜
 * 2019年11月12日
 */
@TableName("t_game")
@Data
public class Game extends Model<Game>{
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
     * 游戏名称
     */
    private String name;
    
    /**
     * 游戏地址
     */
    private String url;
    
    /**
     * 排序号
     */
    @TableField(value = "num_order")
    private Long numOrder;
    /**
     * 显示状态 0-显示 -1-不显示
     */
    private Integer status;
    /**
     * 封面图片地址
     */
    @TableField(value = "cover_url")
    private String coverUrl;
    /**
     *  游戏类型 0-小程序 1-H5
     */
    private Integer type;
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
