package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Accessors(chain = true)
@TableName("device_fence")
public class DeviceFence {

    private Long id;
    private Long deviceId;
    private Long fenceId;
}
