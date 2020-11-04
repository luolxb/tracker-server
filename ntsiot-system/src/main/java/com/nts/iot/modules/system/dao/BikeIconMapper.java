package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.BikeIcon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhc
 * @since 2019-04-16
 */
@Repository
@Mapper
public interface BikeIconMapper extends BaseMapper<BikeIcon> {

}
