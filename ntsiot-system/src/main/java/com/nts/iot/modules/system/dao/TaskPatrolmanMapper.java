package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.TaskPatrolman;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/7/9 10:09
 * @Description:
 */
@Mapper
@Repository
public interface TaskPatrolmanMapper extends BaseMapper<TaskPatrolman> {

}
