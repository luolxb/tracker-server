package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.QuestionnaireOptionMapper;
import com.nts.iot.modules.system.dto.QuestionnaireDTO;
import com.nts.iot.modules.system.model.QuestionnaireOption;
import com.nts.iot.modules.system.service.QuestionnaireOptionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
@Service
public class QuestionnaireOptionServiceImpl extends ServiceImpl<QuestionnaireOptionMapper, QuestionnaireOption> implements QuestionnaireOptionService {

    @Override
    public void saveQuestionnaireOption(QuestionnaireDTO questionnaireDTO) {
        questionnaireDTO.getSelectQuestion().forEach(questionId -> {
            QuestionnaireOption questionnaireOption = new QuestionnaireOption();
            //问卷id
            questionnaireOption.setQuestionnaireId(questionnaireDTO.getId());
            //问题id
            questionnaireOption.setQuestionId(questionId);
            //排序 TODO
            questionnaireOption.setSort(null);
            baseMapper.insert(questionnaireOption);
        });
    }

    @Override
    public void deleteByQuestionnaireId(Long id) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("questionnaire_id", id);
        baseMapper.deleteByMap(columnMap);
    }
}
