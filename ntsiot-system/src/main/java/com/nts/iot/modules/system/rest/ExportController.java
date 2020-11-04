package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.miniApp.service.AppointmentService;
import com.nts.iot.modules.miniApp.service.IllegalReportService;
import com.nts.iot.modules.system.service.BorrowingReturningService;
import com.nts.iot.modules.system.service.HouseRecordSerivice;
import com.nts.iot.modules.system.service.LockService;
import com.nts.iot.aop.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 导出
 */
@Controller
@RequestMapping("download")
public class ExportController {

    @Value("${excelTemplate.path}")
    private String path;

    @Autowired
    private LockService lockService;

    @Autowired
    private BorrowingReturningService borrowingReturningService;

    @Autowired
    private IllegalReportService ilegalReportService;

    @Autowired
    private AppointmentService appointmentService;


    @Autowired
    private HouseRecordSerivice houseRecordSerivice;


    /**
     * 导出车锁
     *
     * @param res
     */
    @Log("导出车锁")
    @GetMapping("exportLock")
    public void export(HttpServletResponse res, @RequestParam("lockBarcode") String lockBarcode, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        lockService.exportLock(res, path, lockBarcode, startTime, endTime);
    }

    /**
     * 导出物品
     *
     * @param res
     */
    @Log("导出物品")
    @GetMapping("exportLoanCount")
    public void exportLoanCount(HttpServletResponse res, @RequestParam("username") String username, @RequestParam("jurisdiction") Long jurisdiction) {
        borrowingReturningService.exportLoanCount(res, path, username, jurisdiction);
    }

    /**
     * 导出违法举报
     *
     * @param res
     */
    @Log("导出违法举报")
    @GetMapping("exportIllegalReport")
    public void exportIllegalReport(HttpServletResponse res, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("jurisdiction") Long jurisdiction) {
        ilegalReportService.exportIllegalReport(res, path, startTime, endTime, jurisdiction);
    }

    /**
     * 导出预约登记列表
     *
     * @param res
     */
    @Log("导出预约登记列表")
    @GetMapping("exportAppointment")
    public void exportAppointment(HttpServletResponse res, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("username") String username, @RequestParam("jurisdiction") Long jurisdiction) {
        appointmentService.exportAppointment(res, path, startTime, endTime, username, jurisdiction);
    }

    @Log("导出房屋备案登记")
    @GetMapping("exportHousingRentalReport")
    public void exportHousingRentalReport(HttpServletResponse res, @RequestParam("owner") String owner, @RequestParam("phone") String phone, @RequestParam("depts") List<String> depts) {
        houseRecordSerivice.exportHousingRentalReport(res, path, owner, phone, depts);
    }

    /**
     * 导出预约登记统计结果
     *
     * @param res
     */
    @Log("导出预约登记统计结果")
    @GetMapping("exportAppointmentStatics")
    public void exportAppointment(HttpServletResponse res,
                                  @RequestParam("startTime") Long startTime,
                                  @RequestParam("endTime") Long endTime,
                                  @RequestParam("deptId") String deptId,
                                  @RequestParam("type") String type) {
        appointmentService.exportAppointmentStatics(res, path, startTime, endTime, type, deptId);
    }
}
