package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.NewDict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @PackageName: com.nts.iot.modules.system.dao
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-07 15:09
 **/
@Repository
@Mapper
public interface NewDictMapper extends BaseMapper<NewDict> {
}
