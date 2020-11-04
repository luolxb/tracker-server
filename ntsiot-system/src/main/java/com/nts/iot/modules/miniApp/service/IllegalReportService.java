package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.model.IllegalReport;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IllegalReportService extends IService<IllegalReport> {

    /**
     * 添加
     *
     * @param illegalReport
     */
    void saveIllegalReport(IllegalReport illegalReport);

    /**
     * 列表查询
     *
     * @param pageable
     * @return
     */
    Map queryAll(Pageable pageable, String startTime, String endTime, List<Long> jurisdiction);

    void exportIllegalReport(HttpServletResponse res, String path, String startTime, String endTime, Long jurisdiction);

    /**
     * 根据年获取12个月 已受理、未受理、无需受理、无效举报
     * @param year
     * @return
     */
    List<Map<String,Object>> getIllegalReport(String year, Set<Long> deptIds);

    Map<String, Object> getStatisticsByType(Long startTime, Long endTime, List<String> depts, String dateType);
    Map<String, Object> getDealStatisticsByType(Long startTime, Long endTime, List<String> depts, String dateType);
}
