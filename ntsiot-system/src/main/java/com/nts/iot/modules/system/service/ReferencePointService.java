package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.ReferencePoint;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface ReferencePointService extends IService<ReferencePoint> {

    Map queryAll(String name, Long iconType, List<String> jurisdiction, Pageable pageable);
}