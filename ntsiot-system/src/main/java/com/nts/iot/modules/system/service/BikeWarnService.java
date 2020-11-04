package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.BikeWarnDTO;
import com.nts.iot.modules.system.model.BikeWarn;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface BikeWarnService extends IService<BikeWarn> {
    Map queryAll(BikeWarnDTO bikeWarnDTO, Set<Long> deptIds, Pageable pageable);
}
