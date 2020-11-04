package com.nts.iot.modules.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel("删除围栏VO")
public class FenceDelVo {

    @NotNull(message = "所属用户id不能为空")
    @ApiModelProperty("所属用户id")
    private Long UserId;

    @Size(message = "围栏id不能为空")
    @ApiModelProperty("围栏ids")
    private List<Long> ids;
}
