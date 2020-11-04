package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.miniApp.model.IllegalReport;
import com.nts.iot.modules.miniApp.service.IllegalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 违法举报
 */
@RestController
@RequestMapping("ma")
public class MaIllegalReportController {

    @Autowired
    private IllegalReportService illegalReportService;

    /**
     * 添加违法举报
     *
     * @param illegalReport
     * @return
     */
    @PostMapping(value = "/illegalReport/save")
    public String save(@RequestBody IllegalReport illegalReport) {
        String result;
        try {
            illegalReportService.saveIllegalReport(illegalReport);
            result = "SUCCESS";
        } catch (Exception e) {
            result = "ERROR";
            e.printStackTrace();
        }
        return result;
    }
}
