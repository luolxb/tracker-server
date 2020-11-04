package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 题库关系表
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:58
 * @Description:
 */
@TableName("question_option")
@Data
public class QuestionOption extends Model<QuestionOption> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目id
     */
    @TableField("question_id")
    private Long questionId;

    /**
     * 选项id
     */
    @TableField("option_id")
    private String optionId;

    /**
     * 选项值
     */
    @TableField("option_val")
    private String optionVal;

}
