package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Questionnaire;
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
public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {

    Page<Questionnaire> selectByPage(Page<Questionnaire> page, @Param("jurisdictions") List<String> jurisdictions, @Param("title") String title);

    /**
     * 改变问题是否显示
     * @param id
     */
    void updataStatus(@Param("id") Long id, @Param("status") String status);
}
