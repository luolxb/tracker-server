package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.BikeLicense;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author zhc@rnstec.com
 * @Description 车辆授权service
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */
public interface BikeLicenseService extends IService<BikeLicense> {

    Map queryAll(String name, String telephone, List<String> jurisdiction, Pageable pageable);

    List<BikeLicense> findAll(String telephone, String jurisdiction);
}
