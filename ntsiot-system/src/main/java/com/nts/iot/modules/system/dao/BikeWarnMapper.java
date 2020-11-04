package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.BikeWarn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 13:23
 * @Description:
 */
@Repository
@Mapper
public interface BikeWarnMapper extends BaseMapper<BikeWarn> {


}
