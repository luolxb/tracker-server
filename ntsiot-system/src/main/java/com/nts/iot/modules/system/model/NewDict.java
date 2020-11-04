package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @PackageName: com.nts.iot.modules.system.model
 * @program: nts
 * @author: ruosen
 * @create: 2020-04-30 13:24
 **/
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
@TableName("new_dict")
public class NewDict extends Model<NewDict> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典code
     */
    private String code;

    /**
     * 字典值
     */
    private String value;

    /**
     * 描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 父节点
     */
    @TableField("p_id")
    private Long pId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 是否启用 1：启用；2：禁用
     */
    private Integer enabled;

    /**
     * 是否删除1：否；2：是
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 子集集合
     */
    @TableField(exist = false)
    private List<NewDict> newDictChild;

}
