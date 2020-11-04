package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.system.model.UserDevice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @PackageName: com.nts.iot.modules.system.dao
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-06 15:17
 **/
@Mapper
@Repository
public interface UserDeviceMapper extends BaseMapper<UserDevice> {
}
