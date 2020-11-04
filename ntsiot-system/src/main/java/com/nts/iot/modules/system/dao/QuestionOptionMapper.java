package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.QuestionOption;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 10:12
 * @Description:
 */
@Mapper
@Repository
public interface QuestionOptionMapper extends BaseMapper<QuestionOption> {
}
