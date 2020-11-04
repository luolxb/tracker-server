package com.nts.iot.modules.system.dto;

import com.nts.iot.modules.system.model.QuestionOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题库
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:58
 * @Description:
 */
@Data
public class QuestionDTO implements Serializable {

    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 是否展示
     */
    private String isShow;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 选项
     */
    List<QuestionOption> options;

    /**
     * 显示顺序
     */
    private Long sort;
}
