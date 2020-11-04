package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.QuestionnaireDTO;
import com.nts.iot.modules.system.model.Questionnaire;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:57
 * @Description:
 */
public interface QuestionnaireService extends IService<Questionnaire> {

    /**
     * 问卷列表
     * @param title
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

    /**
     * 创建问卷
     * @param questionnaireDTO
     */
    @Transactional(rollbackFor = Exception.class)
    void saveQuestionnaire(QuestionnaireDTO questionnaireDTO);

    /**
     * 删除问卷
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    void delete(Long id);

    /**
     * 更改问卷是否显示
     * @param id
     * @param status
     */
    @Transactional(rollbackFor = Exception.class)
    void updataStatus(Long id, String status);


    /**
     * 生成调查问卷
     * @param deptId
     * @return
     */
    Questionnaire createQuestionnaire(Long deptId);
}
