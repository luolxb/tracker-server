package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.nts.iot.modules.system.model.vo.DeviceVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zhc@rnstec.com
 * @Description 限行围栏
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */

@TableName("fence")
@Data
@ApiModel("围栏实体类")
public class Fence extends Model<Fence> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("围栏id")
    private Long id;

    /**
     * 围栏坐标
     */
    @ApiModelProperty("围栏坐标")
    @NotBlank(message = "围栏坐标不能为空")
    private String coordinate;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;


    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String remark;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空")
    @ApiModelProperty("类型")
    private String type;

    /**
     * 所属用户id
     */
    @ApiModelProperty("所属用户")
    @NotNull(message = "所属用户不能为空")
    @TableField("user_id")
    private Long userId;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @NotBlank(message = "状态不能为空")
    @TableField("status")
    private String status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 设备id
     */
    @ApiModelProperty("设备id  list")
    @TableField(exist = false)
    private List<Long> deviceIds;

    @ApiModelProperty("设备list")
    @TableField(exist = false)
    private List<DeviceVo> deviceVos;

//    /**
//     * 辖区名称
//     */
//    @TableField(exist = false)
//    private String deptName;
}
