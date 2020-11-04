package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.VirtualPile;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author zhc@rnstec.com
 * @Description 虚拟桩
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */
public interface VirtualPileService extends IService<VirtualPile> {

    Map queryAll(String name, List<String> jurisdiction, Pageable pageable);

    /**
     * 初始化虚拟桩
     */
    void initPiles();
}
