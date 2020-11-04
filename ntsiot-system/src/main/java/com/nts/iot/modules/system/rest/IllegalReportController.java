package com.nts.iot.modules.system.rest;


import cn.hutool.http.HttpUtil;
import com.nts.iot.modules.miniApp.model.IllegalReport;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.IllegalReportService;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 违法举报
 */
@RestController
@RequestMapping("api")
public class IllegalReportController extends JwtBaseController {
    @Autowired
    private IllegalReportService illegalReportService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private DataScope dataScope;
    @Autowired
    private DeptService deptService;

    /**
     * 查看列表
     *
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    @Log("违法举报查询")
    @GetMapping("/illegalReport/list")
    @PreAuthorize("hasAnyRole('ADMIN','ILLEGAL_REPORT_ALL','ILLEGAL_REPORT_SELECT')")
    public ResponseEntity queryAll(String startTime, String endTime, Pageable pageable) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(illegalReportService.queryAll(pageable, startTime, endTime, list), HttpStatus.OK);
    }

    /**
     * 查询日志详细
     *
     * @return
     */
    @Log("违法举报详细")
    @GetMapping(value = "/illegalReport/findById/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ILLEGAL_REPORT_ALL','ILLEGAL_REPORT_DETAIL')")
    public ResponseEntity getById(@PathVariable Long id) {
        IllegalReport illegalReport = illegalReportService.getById(id);
        if (illegalReport != null) {
            // 图片
            List<Picture> pictureList = pictureService.getFileList(id, "ILLEGAL_REPORT_IMG");
            if (pictureList != null && pictureList.size() > 0) {
                List<String> pathList = new ArrayList<>();
                for (int i = 0; i < pictureList.size(); i++) {
                    pathList.add(pictureList.get(i).getPath());
                }
                illegalReport.setSourceData(pathList);
            }

            // 视频
            List<Picture> videoList = pictureService.getFileList(id, "ILLEGAL_REPORT_VIDEO");
            if (videoList != null && videoList.size() > 0) {
                List<String> vdList = new ArrayList<>();
                for (int i = 0; i < videoList.size(); i++) {
                    vdList.add(videoList.get(i).getPath());
                }
                illegalReport.setVideoList(vdList);
            }

            // 音频
            List<Picture> mp3List = pictureService.getFileList(id, "ILLEGAL_REPORT_MP3");
            if (mp3List != null && mp3List.size() == 1) {
                illegalReport.setFrequency(mp3List.get(0).getPath());
            }
        }

        return new ResponseEntity(illegalReport, HttpStatus.OK);
    }

    /**
     * 修改类型
     *
     * @return
     */
    @Log("违法举报受理")
    @PutMapping(value = "/illegalReport/updateType")
    @PreAuthorize("hasAnyRole('ADMIN','ILLEGAL_REPORT_ALL','ILLEGAL_REPORT_DETAIL')")
    public ResponseEntity updateType(@RequestBody IllegalReport illegalReport, @ModelAttribute("user") User user) {
//        IllegalReport illegalReport = illegalReportService.getById(id);
        illegalReport.setType(illegalReport.getType());
        illegalReport.setUserName(user.getUsername());
        illegalReport.setDealTime(System.currentTimeMillis());
        illegalReportService.updateById(illegalReport);
        // 标记为已受理
        if ("1".equals(illegalReport.getType()) && illegalReport.getTelephone()!=null){
            String deptName = "";
            if (illegalReport.getJurisdiction()!=null){
                Dept dept = deptService.getById(illegalReport.getJurisdiction());
                deptName = dept.getName();
            }
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 hh：mm");
            String time = df.format(new Date(illegalReport.getCreateTime()));
            String content = deptName + "先行联系:您好,您于" + time + " 提交的投诉已收悉，我们会安排相关人员尽快处理，感谢您对社区安全工作的大力支持！";
            sendSMS(illegalReport.getTelephone(),content);
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    /**
     * 违法举报统计（已受理数、未受理数、无需受理数、无效举报数）
     * @param
     * @return
     */
    @Log("违法举报统计")
    @GetMapping("/illegalReportCharts")
    public ResponseEntity getIllegalReport(@RequestParam(required = false) Long startTime,
                                           @RequestParam(required = false) Long endTime,
                                           @RequestParam(required = false) String dateType,
                                           @ModelAttribute("user") User user) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> depts = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            depts.add(String.valueOf(dept.getId()));
        }
        // 按分类统计违法举报
        Map<String, Object> map1 = illegalReportService.getStatisticsByType(startTime, endTime, depts, dateType);
        // 按处理时间统计违法举报
        Map<String, Object> map2 = illegalReportService.getDealStatisticsByType(startTime, endTime, depts, dateType);
        resultMap.put("map1", map1);
        resultMap.put("map2", map2);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    // 利用梦网接口 发送短信
    private static void sendSMS(String phoneNumber,String content){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", "JS3755");
        paramMap.put("password", "159369");
        paramMap.put("pszMobis", phoneNumber);
        paramMap.put("pszMsg", content);
        paramMap.put("iMobiCount", 1);
        paramMap.put("pszSubPort", "******");
        /* ※ 发送短信时 时间戳格式必须是 yyyyMMddHHmmss */
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        paramMap.put("Attime",  df.format(new Date()));
        String result= HttpUtil.post("http://61.145.229.26:8086/MWGate/wmgw.asmx/MongateCsSpSendSmsNew", paramMap);
        System.out.println("短信发送成功："+result);
    }


}
