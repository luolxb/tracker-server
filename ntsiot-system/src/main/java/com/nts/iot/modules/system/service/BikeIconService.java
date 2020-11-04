package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.BikeIcon;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author jie
 * @date 2019-03-25
 */
public interface BikeIconService extends IService<BikeIcon> {

    Map queryAll(String name, List<String> depts, Pageable pageable);

    BikeIcon findById(Long id);
}