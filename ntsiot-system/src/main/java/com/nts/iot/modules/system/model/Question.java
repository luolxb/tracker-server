package com.nts.iot.modules.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * 题库
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:58
 * @Description:
 */
@TableName("question")
@Data
public class Question extends Model<Question> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * 问题类型
     */
    @TableField("question_type")
    private String questionType;

    /**
     * 是否展示
     */
    @TableField("is_show")
    private String isShow;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 选项
     */
    @TableField(exist = false)
    List<QuestionOption> options;

    /**
     * 显示顺序
     */
    private Long sort;

    /**
     * 辖区
     */
    private Long jurisdiction;
}
