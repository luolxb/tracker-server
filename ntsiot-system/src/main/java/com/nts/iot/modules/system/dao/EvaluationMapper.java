package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.EvaluationDTO;
import com.nts.iot.modules.system.model.Evaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 13:23
 * @Description:
 */
@Repository
@Mapper
public interface EvaluationMapper extends BaseMapper<Evaluation> {
    Page<EvaluationDTO> selectByPage(Page<EvaluationDTO> page, @Param("jurisdictions") List<String> jurisdictions, @Param("title") String title);

    List<EvaluationDTO> getEvaluationsByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);

    String getAnswer(@Param("questionId") Long questionId, @Param("selectedOption") List<String> selectedOption);
}
