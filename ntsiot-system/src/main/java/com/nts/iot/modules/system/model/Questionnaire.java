
package com.nts.iot.modules.system.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * 调查问卷
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:58
 * @Description:
 */
@TableName("questionnaire")
@Data
public class Questionnaire extends Model<Questionnaire> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * 是否展示
     */
    @TableField("is_show")
    private String isShow;


    /**
     * 辖区
     */
    private Long jurisdiction;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    @TableField(exist = false)
    List<Question> questions;


}
