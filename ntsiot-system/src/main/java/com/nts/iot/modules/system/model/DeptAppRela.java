package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/28 15:25
 * @Description:
 */
@TableName("dept_modules")
@Data
public class DeptAppRela extends Model<DeptAppRela> {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("dept_id")
    private Long deptId;

    @TableField("app_module_id")
    private Long appModuleId;
}
