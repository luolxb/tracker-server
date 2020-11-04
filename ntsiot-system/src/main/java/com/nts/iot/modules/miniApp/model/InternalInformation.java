package com.nts.iot.modules.miniApp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * 内部信息上传
 */
@Data
@TableName("internal_information")
public class InternalInformation extends Model<InternalInformation> {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 身份证号
     */
    @TableField("id_code")
    private String idCode;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 居住地址
     */
    @TableField("address")
    private String address;

    /**
     * 图片urlList
     */
    @TableField(exist = false)
    private List<String> sourceData;


    /**
     * 视频urlList
     */
    @TableField(exist = false)
    private List<String> videoList;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 辖区编号
     */
    @TableField("jurisdiction")
    private Long jurisdiction;
}
