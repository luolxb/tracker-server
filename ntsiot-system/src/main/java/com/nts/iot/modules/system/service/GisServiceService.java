package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.GisService;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.GisServiceRq;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GisServiceService extends IService<GisService> {

    Map queryPage(String type, String name, Long userId, Pageable pageable);

    void create(GisServiceRq gisServiceRq, User user);

    void delete(String ids, User user);
}
