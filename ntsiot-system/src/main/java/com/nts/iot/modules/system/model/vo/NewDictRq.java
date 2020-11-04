package com.nts.iot.modules.system.model.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@ApiModel("新数据字典")
@Accessors(chain = true)
public class NewDictRq extends Model<NewDictRq> {

    @ApiModelProperty(value = "字典id")
    private Long id;

    /**
     * 字典code
     */
    @ApiModelProperty(value = "字典code")
    private String code;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private String value;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 父节点
     */
    @ApiModelProperty(value = "父节点")
    private Long pId;

}
