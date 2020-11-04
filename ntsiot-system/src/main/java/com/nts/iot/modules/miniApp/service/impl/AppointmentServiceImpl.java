package com.nts.iot.modules.miniApp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.nts.iot.modules.miniApp.dao.AppointmentMapper;
import com.nts.iot.modules.miniApp.model.Appointment;
import com.nts.iot.modules.miniApp.service.AppointmentService;
import com.nts.iot.modules.system.dto.AppointmentStaticDTO;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.util.ExcelExportUtil;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/5/29 10:19
 * @Description:
 */
@Slf4j
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Autowired
    DeptService deptService;

    @Override
    public Map queryAll(Pageable pageable, String username, String startTime, String endTime, List<String> jurisdictions) {
        Page<Appointment> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Appointment> pageResult = baseMapper.selectByPage(page, username, startTime, endTime, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public Appointment getLatestAppointment(String openId) {
        return baseMapper.getLatestAppointment(openId);
    }

    @Override
    public List<AppointmentStaticDTO> appointmentStatic(Long startTime, Long endTime, String type, String deptId) {
        //获取选中区域的全部子区域
        List<Long> allDeptIds = deptService.getSubDepts(Long.valueOf(deptId));
        if (CollectionUtils.isEmpty(allDeptIds)) {
            return Lists.newArrayList();
        }
        //按照天
        if (isByDay(type)) {
            int diffDay = (int) (Math.ceil(1.0 * (endTime - startTime) / (24 * 60 * 60 * 1000)));
            diffDay = diffDay > 30 ? 30 : diffDay;
            return baseMapper.appointmentStaticByDay(diffDay, startTime, endTime, allDeptIds);
        }
        //按照月
        if (isByMonth(type)) {
            int diffMonth = (int) (Math.ceil(1.0 * (endTime - startTime) / (24 * 60 * 60 * 1000) / 30));
            diffMonth = diffMonth > 12 ? 12 : diffMonth;
            return baseMapper.appointmentStaticByMonth(diffMonth, startTime, endTime, allDeptIds);
        }

        return Lists.newArrayList();
    }

    @Override
    public void exportAppointmentStatics(HttpServletResponse res, String path, Long startTime, Long endTime, String type, String deptId) {
        List<AppointmentStaticDTO> appointmentStatic = appointmentStatic(startTime, endTime, type, deptId);
        try (OutputStream outputStream = res.getOutputStream()) {
            if (CollectionUtils.isEmpty(appointmentStatic)) {
                outputStream.write("暂无数据".getBytes());
                return;
            }
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("预约登记人数统计");
            sheet.setDefaultColumnWidth(20);

            HSSFCellStyle styleMain = wb.createCellStyle();
            styleMain.setAlignment(HorizontalAlignment.CENTER);

            HSSFCellStyle leftStyle = wb.createCellStyle();
            leftStyle.setAlignment(HorizontalAlignment.LEFT);

            HSSFRow row1 = sheet.createRow(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
            HSSFCell header = row1.createCell((short) 0);
            header.setCellValue("预约登记人数统计表");
            header.setCellStyle(styleMain);

            HSSFRow title = sheet.createRow(1);
            HSSFCell indexCell = title.createCell((short) 0);
            indexCell.setCellValue("序号");
            indexCell.setCellStyle(leftStyle);

            HSSFCell dateCell = title.createCell((short) 1);
            dateCell.setCellValue("日期");
            dateCell.setCellStyle(leftStyle);

            HSSFCell countCell = title.createCell((short) 2);
            countCell.setCellValue("总人数");
            countCell.setCellStyle(leftStyle);

            for (int i = 0; i < appointmentStatic.size(); i++) {
                HSSFRow dataRow = sheet.createRow(i + 2);
                HSSFCell index = dataRow.createCell((short) 0);
                index.setCellValue(i + 1);
                index.setCellStyle(leftStyle);

                HSSFCell date = dataRow.createCell((short) 1);
                date.setCellValue(appointmentStatic.get(i).getDt());
                date.setCellStyle(leftStyle);

                HSSFCell count = dataRow.createCell((short) 2);
                count.setCellValue(appointmentStatic.get(i).getTotalNum());
                count.setCellStyle(leftStyle);
            }
            res.setContentType("application/octet-stream");
            res.setHeader("Content-disposition", "attachment;filename=" + "appointmentStatics.xls");
            res.flushBuffer();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("导出预约统计结果异常", e);
        }
    }

    @Override
    public HashMap<String, Long> countStatusByjurisdictions(long jurisdiction) {
        List<Long> subDepts = deptService.getSubDepts(jurisdiction);
        HashMap<String, Long> res = baseMapper.countStatusByjurisdictions(subDepts);
        return res;
    }

    private boolean isByMonth(String type) {
        return "month".equalsIgnoreCase(type);
    }

    private boolean isByDay(String type) {
        return "day".equalsIgnoreCase(type);
    }

    /**
     * 导出
     *
     * @param res
     * @param startTime
     * @param endTime
     * @param jurisdiction
     */
    @Override
    public void exportAppointment(HttpServletResponse res, String path, String startTime, String endTime, String username, Long jurisdiction) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("jurisdiction", jurisdiction);
        if (!StringUtils.isEmpty(startTime)) {
            queryWrapper.ge("create_time", startTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            queryWrapper.le("create_time", endTime);
        }
        if (!StringUtils.isEmpty(username)) {
            queryWrapper.like("username", username);
        }
        List<Appointment> appointments = this.list(queryWrapper);
        String templateFilePath = path + File.separator + "appointment.xlsx";
        if (appointments != null && appointments.size() > 0) {
            ExcelExportUtil excel = new ExcelExportUtil();
            try {
                excel.writeData(templateFilePath, null, 0);
                List<Map<Integer, Object>> datalist = new ArrayList<>();
                for (Appointment appointment : appointments) {
                    Map<Integer, Object> data = new HashMap<>();
                    // 1.登记人姓名
                    data.put(1, appointment.getUsername());
                    // 2.登记人联系电话
                    data.put(2, appointment.getPhone());
                    // 3.预约时间
                    data.put(3, appointment.getAppointmentDate() + " " + appointment.getAppointmentTime());
                    // 4.居住地址
                    data.put(4, appointment.getAddress());
                    // 5.受理状态
                    // 0: 待受理 1: 已受理
                    if ("0".equals(appointment.getStatus())) {
                        data.put(5, "待受理");
                    } else if ("1".equals(appointment.getStatus())) {
                        data.put(5, "已受理");
                    }
                    DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh：mm");
                    String time = df.format(new Date(appointment.getCreateTime()));
                    // 6.登记时间
                    data.put(6, time);
                    Dept myDept = deptService.getById(appointment.getJurisdiction());
                    if (myDept != null) {
                        // 7.所属辖区
                        data.put(7, myDept.getName());
                        if (myDept.getPid() != null) {
                            Dept pDept = deptService.getById(myDept.getPid());
                            if (pDept != null) {
                                // 父级辖区
                                data.put(8, pDept.getName());
                            } else {
                                // 父级辖区
                                data.put(8, "");
                            }
                        } else {
                            // 父级辖区
                            data.put(8, "");
                        }
                    } else {
                        // 所属辖区
                        data.put(7, "");
                        // 父级辖区
                        data.put(8, "");
                    }

                    datalist.add(data);
                }
                String[] heads = new String[]{"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3"};
                excel.writeDateList(templateFilePath, heads, datalist, 0, res, "预约登记");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
