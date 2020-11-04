package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.QuestionnaireMapper;
import com.nts.iot.modules.system.dto.QuestionnaireDTO;
import com.nts.iot.modules.system.model.Question;
import com.nts.iot.modules.system.model.QuestionOption;
import com.nts.iot.modules.system.model.Questionnaire;
import com.nts.iot.modules.system.service.QuestionOptionService;
import com.nts.iot.modules.system.service.QuestionService;
import com.nts.iot.modules.system.service.QuestionnaireOptionService;
import com.nts.iot.modules.system.service.QuestionnaireService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {

    @Autowired
    private QuestionOptionService questionOptionService;

    @Autowired
    private QuestionService questionService;

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<Questionnaire> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Questionnaire> pageResult = baseMapper.selectByPage(page, jurisdictions, title);
        return PageUtil.toPage(pageResult);
    }

    @Autowired
    QuestionnaireOptionService questionnaireOptionService;

    @Override
    public void saveQuestionnaire(QuestionnaireDTO questionnaireDTO) {
        Questionnaire questionnaire = new Questionnaire();
        // 问卷标题
        questionnaire.setTitle(questionnaireDTO.getTitle());
        // 辖区
        questionnaire.setJurisdiction(questionnaireDTO.getJurisdiction());
        // 是否显示 默认显示0
        questionnaire.setIsShow("0");
        // 创建时间
        questionnaire.setCreateTime(System.currentTimeMillis());
        // 保存问卷
        int result = baseMapper.insert(questionnaire);
        if (result > 0) {
            // 问卷id
            questionnaireDTO.setId(questionnaire.getId());
            // 保存问卷题目
            questionnaireOptionService.saveQuestionnaireOption(questionnaireDTO);
        }
    }

    @Override
    public void delete(Long id) {
        //删除问卷题目表
        questionOptionService.deleteByQuestionId(id);
        //删除问卷表
        baseMapper.deleteById(id);
    }

    @Override
    public void updataStatus(Long id, String status) {
        baseMapper.updataStatus(id, status);
    }

    @Override
    public Questionnaire createQuestionnaire(Long deptId) {
        // 根据辖区查找问卷，每个辖区暂定只显示一个问卷 TODO
        Questionnaire questionnaire = null;
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("jurisdiction", deptId);
        columnMap.put("is_show", 0);
        List<Questionnaire> list = baseMapper.selectByMap(columnMap);
        if (list != null && list.size() > 0) {
            //获取问卷
            questionnaire = list.get(0);
            // 根据问卷获取题目
            List<Question> questions = questionService.getQuestionByQuestionnaireId(questionnaire.getId());
            // 根据题目获取选项
            questions.forEach(it -> {
                List<QuestionOption> questionOptions = questionOptionService.getOtionsByQuestionId(it.getId());
                it.setOptions(questionOptions);
            });
            questionnaire.setQuestions(questions);
        }
        return questionnaire;
    }
}
