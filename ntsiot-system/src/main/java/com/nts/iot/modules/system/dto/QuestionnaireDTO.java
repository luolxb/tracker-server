package com.nts.iot.modules.system.dto;

import com.nts.iot.modules.system.model.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/5 11:13
 * @Description:
 */
@Data
public class QuestionnaireDTO implements Serializable {

    /**
     * 调查问卷id
     */
    private Long id;

    /**
     * 问卷标题
     */
    private String title;

    /**
     * 所选问题
     */
    private List<Long> selectQuestion;

    /**
     * 辖区
     */
    private Long jurisdiction;

    List<Question> questions;
}
