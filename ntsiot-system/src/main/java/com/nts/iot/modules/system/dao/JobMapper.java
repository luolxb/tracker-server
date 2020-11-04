package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.Job;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzm
 * @since 2019-04-16
 */
@Mapper
@Repository
public interface JobMapper extends BaseMapper<Job> {

//    List<Job> queryAll(@Param("name") String name, @Param("enabled") Boolean enabled, @Param("deptIds") Set<Long> deptIds, @Param("deptId") Long deptId);
}
