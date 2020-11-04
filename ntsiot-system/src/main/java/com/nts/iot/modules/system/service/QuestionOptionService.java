package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.QuestionOption;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
public interface QuestionOptionService extends IService<QuestionOption> {

    /**
     * 根据问题查找选项
     * @param questionId
     * @return
     */
    List<QuestionOption> getOtionsByQuestionId(Long questionId);

    /**
     * 根据问卷删除选项
     * @param questionnaireId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer deleteByQuestionId(Long questionnaireId);
}
