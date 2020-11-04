package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.QuestionDTO;
import com.nts.iot.modules.system.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 10:12
 * @Description:
 */
@Mapper
@Repository
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 列表查询
     * @param page
     * @param title
     * @return
     */
    Page<QuestionDTO> selectByPage(Page<QuestionDTO> page, @Param("title") String title, @Param("jurisdictions") List<String> jurisdictions);

    /**
     * 根据辖区获取问卷问题列表
     * @param deptId
     * @return
     */
    List<Question> getQuestion(@Param("deptId") Long deptId);

    /**
     * 改变问题是否显示
     * @param id
     */
    void updataStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 根据问卷id获取问题
     * @param questionnaireId
     * @return
     */
    List<Question> getQuestionByQuestionnaireId(@Param("questionnaireId") Long questionnaireId);
}
