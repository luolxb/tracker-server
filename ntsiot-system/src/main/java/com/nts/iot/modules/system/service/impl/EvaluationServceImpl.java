package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.EvaluationMapper;
import com.nts.iot.modules.system.dto.EvaluationDTO;
import com.nts.iot.modules.system.model.Evaluation;
import com.nts.iot.modules.system.service.EvaluationService;
import com.nts.iot.modules.system.service.QuestionService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 13:22
 * @Description:
 */
@Service
public class EvaluationServceImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationService {

    @Autowired
    private QuestionService questionService;

    @Override
    public void saveAnswer(List<EvaluationDTO> answers) {
        for (EvaluationDTO evaluationDTO : answers) {
            Evaluation evaluation = new Evaluation();
            // 问卷id
            evaluation.setQuestionnaireId(evaluationDTO.getQuestionnaireId());
            // 题目id
            evaluation.setQuestionId(evaluationDTO.getQuestionId());
            // 创建时间
            evaluation.setCreateTime(System.currentTimeMillis());
            // 答案
            String answerStr = String.valueOf(evaluationDTO.getAnswerList()).replace("[", "").replace("]", "");
            evaluation.setAnswer(evaluationDTO.getAnswer() != null? evaluationDTO.getAnswer(): answerStr);
            // 回答人
            evaluation.setRespondents(evaluationDTO.getRespondents());
            baseMapper.insert(evaluation);
        }
    }

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<EvaluationDTO> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<EvaluationDTO> pageResult = baseMapper.selectByPage(page, jurisdictions, title);
        List<EvaluationDTO> list = pageResult.getRecords();
        for (EvaluationDTO evaluationDTO : list) {
            // 根据问卷查找评价内容
            List<EvaluationDTO> result = this.getEvaluationsByQuestionnaireId(evaluationDTO.getQuestionnaireId());
            List<Map<String, String>> maps = new ArrayList<>();
            for (int index = 0; index < result.size(); index++){
                EvaluationDTO evaluation = result.get(index);
                Map<String, String> map = new HashMap<>();
                // 问题标题
                map.put("questionTitle", evaluation.getQuestionTitle());
                // 答案
                if ("3".equals(evaluation.getQuestionType())) {
                    //输入框
                    map.put("answer", evaluation.getAnswer());
                } else {
                    // 单选、多选
                    List<String> selectedOption = Arrays.asList(evaluation.getAnswer().replace(" ", "").split(","));
                    String answer = this.getAnswer(evaluation.getQuestionId(), selectedOption);
                    map.put("answer", answer);
                }
                maps.add(map);
            }
            evaluationDTO.setEvaluations(maps);
        }
        return PageUtil.toPage(pageResult);
    }

    /**
     * 根据问卷查找评价内容
     * @param questionnaireId
     * @return
     */
    List<EvaluationDTO> getEvaluationsByQuestionnaireId(Long questionnaireId) {
        return baseMapper.getEvaluationsByQuestionnaireId(questionnaireId);
    }

    /**
     * 查找答案
     * @param questionId
     * @param selectedOption
     * @return
     */
    String getAnswer(Long questionId, List<String> selectedOption) {
        return baseMapper.getAnswer(questionId, selectedOption);
    }
}
