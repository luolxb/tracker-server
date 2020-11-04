package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
public interface QuestionService extends IService<Question> {

    /**
     * 查询列表
     * @param title
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

    /**
     * 新增问题
     * @param question
     */
    @Transactional(rollbackFor = Exception.class)
    void saveQuestion(Question question);

    /**
     * 根据辖区获取问卷问题列表
     * @param deptId
     * @return
     */
    List<Question> getQuestion(Long deptId);

    /**
     * 删除问题
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    Integer delete(Long id);

    /**
     * 更改状态
     * @param id
     * @param status
     */
    @Transactional(rollbackFor = Exception.class)
    void updataStatus(Long id, String status);

    /**
     * 根据问卷id查找题目
     * @return
     */
    List<Question> getQuestionByQuestionnaireId(Long questionnaireId);
}
