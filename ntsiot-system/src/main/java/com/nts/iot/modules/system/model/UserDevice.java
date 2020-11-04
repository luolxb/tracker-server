package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @PackageName: com.nts.iot.modules.system.model
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:15
 **/
@TableName("user_device")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserDevice {

    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("device_id")
    private Long deviceId;
}
