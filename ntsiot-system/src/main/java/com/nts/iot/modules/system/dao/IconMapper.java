package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Icon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


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
public interface IconMapper extends BaseMapper<Icon> {

    Page<Icon> selectByPage(Page<Icon> page, @Param("name") String name, @Param("jurisdictions") List<String> jurisdictions);

}
