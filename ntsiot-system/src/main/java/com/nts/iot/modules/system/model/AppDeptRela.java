package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 小程序配置和辖区关系表
 */
@Data
@TableName("app_dept_rela")
public class AppDeptRela extends Model<AppDeptRela> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("app_id")
    private Long appId;

    @TableField("dept_id")
    private Long deptId;
}
