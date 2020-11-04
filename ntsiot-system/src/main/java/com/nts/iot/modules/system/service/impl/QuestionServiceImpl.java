package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.QuestionMapper;
import com.nts.iot.modules.system.dto.QuestionDTO;
import com.nts.iot.modules.system.model.Question;
import com.nts.iot.modules.system.service.QuestionOptionService;
import com.nts.iot.modules.system.service.QuestionService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private QuestionOptionService questionOptionService;

    @Override
    public Map queryAll(String title, List<String> jurisdictions, Pageable pageable) {
        Page<QuestionDTO> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<QuestionDTO> pageResult = baseMapper.selectByPage(page, title, jurisdictions);
        for (QuestionDTO record : pageResult.getRecords()) {
            record.setOptions(questionOptionService.getOtionsByQuestionId(record.getId()));
        }
        return PageUtil.toPage(pageResult);
    }

    @Override
    public void saveQuestion(Question question) {
        question.setCreateTime(System.currentTimeMillis());
        // 保存题目
        baseMapper.insert(question);
        // 保存选项
        question.getOptions().forEach(it -> {
            if(StrUtil.isNotEmpty(it.getOptionId())) {
                it.setQuestionId(question.getId());
                questionOptionService.save(it);
            }
        });
    }

    @Override
    public List<Question> getQuestion(Long deptId) {
        List<Question> questions = baseMapper.getQuestion(deptId);
        questions.forEach(it -> {
            it.setOptions(questionOptionService.getOtionsByQuestionId(it.getId()));
        });
        return questions;
    }

    @Override
    public Integer delete(Long id) {
        questionOptionService.deleteByQuestionId(id);
        return baseMapper.deleteById(id);
    }

    @Override
    public void updataStatus(Long id, String status) {
        baseMapper.updataStatus(id, status);
    }

    @Override
    public List<Question> getQuestionByQuestionnaireId(Long questionnaireId) {
        return baseMapper.getQuestionByQuestionnaireId(questionnaireId);
    }
}
