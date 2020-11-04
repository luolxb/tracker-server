package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author jie
 * @date 2019-03-25
 */
@Mapper
@Repository
public interface DictMapper extends BaseMapper<Dict> {

}
