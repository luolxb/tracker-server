package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.HousingTenantRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:13
 * @Description:
 */
@Repository
@Mapper
public interface HousingTenantRecordMapper extends BaseMapper<HousingTenantRecord> {

}
