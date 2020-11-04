package com.nts.iot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author jie
 * @date 2018-11-24
 */
@Data
@TableName("log")
@ApiModel("系统日志")
public class LogMybatis{

    @ApiModelProperty("日志ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作用户
     */
    @ApiModelProperty("操作用户")
    private String username;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 方法名
     */
    @ApiModelProperty("方法名")
    private String method;

    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private String params;

    /**
     * 日志类型
     */
    @ApiModelProperty("日志类型")
    @TableField("log_type")
    private String logType;

    /**
     * 请求ip
     */
    @ApiModelProperty("请求ip")
    @TableField("request_ip")
    private String requestIp;

    /**
     * 请求耗时
     */
    @ApiModelProperty("请求耗时")
    private Long time;

    /**
     * 异常详细
     */
    @ApiModelProperty("异常详细")
    @TableField("exception_detail")
    private String exceptionDetail;

    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    @TableField("create_time")
    private Timestamp createTime;

//    public LogMybatis(String logType, Long time) {
//        this.logType = logType;
//        this.time = time;
//    }
}
