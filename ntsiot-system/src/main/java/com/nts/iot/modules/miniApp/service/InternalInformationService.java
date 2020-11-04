package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.InternalInformation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface InternalInformationService extends IService<InternalInformation> {

    /**
     * 添加
     *
     * @param internalInformation
     */
    void saveInternalInformation(InternalInformation internalInformation);

    /**
     * 列表查询
     *
     * @param pageable
     * @return
     */
    Map queryAll(Pageable pageable, String startTime, String endTime, List<Long> jurisdiction);
}
