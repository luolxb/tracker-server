package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 满意度评价
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 10:05
 * @Description:
 */
@TableName("evaluation")
@Data
public class Evaluation extends Model<Evaluation> {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问卷id
     */
    @TableField("questionnaire_id")
    private Long questionnaireId;

    /**
     * 题目id
     */
    @TableField("question_id")
    private Long questionId;

    /**
     * 答案
     */
    private String answer;

    /**
     * 回答者
     */
    private Long respondents;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;
}