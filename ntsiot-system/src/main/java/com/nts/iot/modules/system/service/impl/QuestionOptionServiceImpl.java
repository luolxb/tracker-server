package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.QuestionOptionMapper;
import com.nts.iot.modules.system.model.QuestionOption;
import com.nts.iot.modules.system.service.QuestionOptionService;
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
public class QuestionOptionServiceImpl extends ServiceImpl<QuestionOptionMapper, QuestionOption> implements QuestionOptionService {

    @Override
    public List<QuestionOption> getOtionsByQuestionId(Long questionId) {
        QueryWrapper<QuestionOption> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Integer deleteByQuestionId(Long questionId) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("question_id", questionId);
        return baseMapper.deleteByMap(columnMap);
    }
}
