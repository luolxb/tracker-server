package com.nts.iot.modules.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 14:28
 * @Description:
 */
@Data
public class EvaluationDTO implements Serializable {

    private Long id;

    /**
     * 调查问卷id
     */
    private Long questionnaireId;

    /**
     * 调查问卷标题
     */
    private String questionnaireTitle;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 问题
     */
    private String questionTitle;

    /**
     * 问题类型
     */
    private String questionType;

    /**
     * 答案
     */
    private String answer;

    /**
     * 回答者
     */
    private Long respondents;

    /**
     * 回答者
     */
    private String respondentsName;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 答案list
     */
    private List<String> answerList;

    /**
     * 评价
     */
    private List<Map<String, String>> evaluations;
}
