package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.HoseOwnerInfoDto;
import com.nts.iot.modules.system.model.HousingOwnerRecord;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:04
 * @Description:
 */
public interface HouseRecordSerivice extends IService<HousingOwnerRecord> {

    Map queryAll(String owner, String phone, List<String> depts, Pageable pageable);

    void saveForm(HoseOwnerInfoDto formData);

    Map<String, Object> getStatisticsByType(Long startTime, Long endTime, List<String> depts, String dateType);

    void exportHousingRentalReport(HttpServletResponse res, String path, String owner, String phone, List<String> depts);
}
