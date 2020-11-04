package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 问卷关系表
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:58
 * @Description:
 */
@TableName("questionnaire_option")
@Data
public class QuestionnaireOption extends Model<QuestionnaireOption> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 调查问卷id
     */
    @TableField("questionnaire_id")
    private Long questionnaireId;

    /**
     * 题目id
     */
    @TableField("question_id")
    private Long questionId;

    /**
     * 显示顺序
     */
    private Long sort;
}
