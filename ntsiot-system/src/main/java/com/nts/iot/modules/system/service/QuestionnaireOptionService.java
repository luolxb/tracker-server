package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.QuestionnaireDTO;
import com.nts.iot.modules.system.model.QuestionnaireOption;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
public interface QuestionnaireOptionService extends IService<QuestionnaireOption> {

    /**
     * 保存问卷题目
     * @param questionnaireDTO
     */
    void saveQuestionnaireOption(QuestionnaireDTO questionnaireDTO);

    /**
     * 删除问卷题目
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteByQuestionnaireId(Long id);
}
