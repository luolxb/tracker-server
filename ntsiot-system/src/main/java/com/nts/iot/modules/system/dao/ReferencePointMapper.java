package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.ReferencePoint;
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
public interface ReferencePointMapper extends BaseMapper<ReferencePoint> {

    Page<ReferencePoint> selectByPage(Page<ReferencePoint> page, @Param("name") String name, @Param("iconType") Long iconType, @Param("jurisdictions") List<String> jurisdictions);

}
