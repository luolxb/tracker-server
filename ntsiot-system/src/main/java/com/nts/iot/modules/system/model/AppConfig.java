package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 小程序配置表
 */
@Data
@TableName("app_config")
public class AppConfig extends Model<AppConfig> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("app_id")
    private String appId;

    private String secret;

    private String token;

    @TableField("aes_key")
    private String aesKey;

    @TableField("is_default")
    private String isDefault;
}
