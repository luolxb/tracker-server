package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.EvaluationDTO;
import com.nts.iot.modules.system.model.Evaluation;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 13:22
 * @Description:
 */
public interface EvaluationService extends IService<Evaluation> {

    /**
     * 保存问卷答案
     * @param answers
     */
    @Transactional(rollbackFor = Exception.class)
    void saveAnswer(List<EvaluationDTO> answers);

    /**
     * 答案列表
     * @param title
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

}
