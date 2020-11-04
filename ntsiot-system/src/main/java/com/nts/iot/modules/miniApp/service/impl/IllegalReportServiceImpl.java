package com.nts.iot.modules.miniApp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.service.IllegalReportService;
import com.nts.iot.modules.miniApp.dao.IllegalReportMapper;
import com.nts.iot.modules.miniApp.dto.IllegalStatisticsDTO;
import com.nts.iot.modules.miniApp.dto.Serie;
import com.nts.iot.modules.miniApp.model.IllegalReport;
import com.nts.iot.modules.miniApp.model.Picture;
import com.nts.iot.modules.miniApp.service.PictureService;
import com.nts.iot.util.ExcelExportUtil;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@Transactional
public class IllegalReportServiceImpl extends ServiceImpl<IllegalReportMapper, IllegalReport> implements IllegalReportService {
    @Autowired
    private PictureService pictureService;

    @Override
    public void saveIllegalReport(IllegalReport illegalReport) {
        illegalReport.setType("0");
        illegalReport.setCreateTime(System.currentTimeMillis());
        baseMapper.insert(illegalReport);
        // 主键
        Long pk = illegalReport.getId();

        // 插入图片
        if (pk != null && illegalReport.getSourceData() != null && illegalReport.getSourceData().size() > 0) {
            // 循环图片path
            for (int i = 0; i < illegalReport.getSourceData().size(); i++) {
                Picture wdf = new Picture();
                wdf.setPk(pk);
                wdf.setPath(illegalReport.getSourceData().get(i));
                wdf.setType(getType(illegalReport.getSourceData().get(i)));
                pictureService.saveFile(wdf);
            }
        }

        // 插入语音
        if (illegalReport.getFrequency() != null) {
            Picture wdf = new Picture();
            wdf.setPk(pk);
            wdf.setPath(illegalReport.getFrequency());
            wdf.setType(getType(illegalReport.getFrequency()));
            pictureService.saveFile(wdf);
        }
    }

    @Override
    public Map queryAll(Pageable pageable, String startTime, String endTime, List<Long> jurisdiction) {
        Page<IllegalReport> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<IllegalReport> pageResult = baseMapper.selectByPage(page, startTime, endTime, jurisdiction);
//        IPage<IllegalReport> pageResult;
//        pageResult = page(page,
//                new QueryWrapper<IllegalReport>()
//                        .and(jurisdiction.size()>0,wrapper->{
//                            for(int i=0;i<jurisdiction.size();i++){
//                                wrapper.eq("jurisdiction", jurisdiction.get(i));
//                                if(i!=jurisdiction.size()-1){
//                                    wrapper.or();
//                                }
//                            }
//                            return  wrapper;
//                        })
//                        .eq(StringUtils.isNotBlank(startTime),"create_time",startTime)
//                        .eq(StringUtils.isNotBlank(startTime),"create_time",endTime));
        if (pageResult != null && pageResult.getRecords() != null && pageResult.getRecords().size() > 0) {
            for (int i = 0; i < pageResult.getRecords().size(); i++) {
                String type = pageResult.getRecords().get(i).getType();
                if ("0".equals(type)) {
                    pageResult.getRecords().get(i).setTypeName("未受理");
                }
                if ("1".equals(type)) {
                    pageResult.getRecords().get(i).setTypeName("已受理");
                }
                if ("2".equals(type)) {
                    pageResult.getRecords().get(i).setTypeName("无需受理");
                }
                if ("3".equals(type)) {
                    pageResult.getRecords().get(i).setTypeName("无效举报");
                }
            }
        }
        return PageUtil.toPage(pageResult);
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
    public void exportIllegalReport(HttpServletResponse res, String path, String startTime, String endTime, Long jurisdiction) {
        List<IllegalReport> illegalReportList = baseMapper.selectIllegalReport(startTime, endTime, jurisdiction);
        String templateFilePath = path + File.separator + "illegalReport.xlsx";
        if (illegalReportList != null && illegalReportList.size() > 0) {
            ExcelExportUtil excel = new ExcelExportUtil();
            try {
                excel.writeData(templateFilePath, null, 0);
                List<Map<Integer, Object>> datalist = new ArrayList<Map<Integer, Object>>();
                for (int i = 0; i < illegalReportList.size(); i++) {
                    Map<Integer, Object> data = new HashMap<Integer, Object>();
                    data.put(1, illegalReportList.get(i).getName());
                    data.put(2, illegalReportList.get(i).getContent());
                    data.put(3, illegalReportList.get(i).getTelephone());
                    data.put(4, illegalReportList.get(i).getAddress());
                    data.put(5, illegalReportList.get(i).getTime());
                    data.put(6, illegalReportList.get(i).getJurisdictionName());
                    data.put(7, illegalReportList.get(i).getPname());
                    data.put(8, getState(illegalReportList.get(i).getType()));
                    data.put(9, illegalReportList.get(i).getUserName());
                    datalist.add(data);
                }
                String[] heads = new String[]{"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "I3"};
                excel.writeDateList(templateFilePath, heads, datalist, 0, res, "违法举报");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据年获取12个月  0:未受理，1：已受理 2：无需受理 3：无效举报
     *
     * @param year
     * @return
     */
    @Override
    public List<Map<String,Object>> getIllegalReport(String year, Set<Long> deptIds) {
        /* 获取本年12个月开始和结束时间戳 */
        List<Map<String,Object>> stampList = getTwelveStampList(Integer.valueOf(year));
        /* 0 已受理 */
        List<Integer> size0 = getCountByType(stampList,"0",deptIds);
        /* 1 未受理 */
        List<Integer> size1 = getCountByType(stampList,"1",deptIds);
        /* 2 无需受理 */
        List<Integer> size2 = getCountByType(stampList,"2",deptIds);
        /* 3 无效举报 */
        List<Integer> size3 = getCountByType(stampList,"3",deptIds);

        List<Map<String,Object>> series = new ArrayList<>();
        Map<String,Object> map0 = new HashMap<>();
        map0.put("name","已受理");
        map0.put("type","bar");
        map0.put("data",size0);
        series.add(map0);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("name","未受理");
        map1.put("type","bar");
        map1.put("data",size1);
        series.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("name","无需受理");
        map2.put("type","bar");
        map2.put("data",size2);
        series.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("name","无效举报");
        map3.put("type","bar");
        map3.put("data",size3);
        series.add(map3);

        return series;
    }

    // 获取12个月的 开始、结束时间戳
    private List<Map<String,Object>> getTwelveStampList(Integer year){
        List<Map<String,Object>> stampList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            //n 月的第一天
            LocalDate firstDay = LocalDate.of(year, i, 1);
            //n 月的最后一天
            LocalDate lastTheMonthDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
            //n 月的 第一天 零点
            Long todayStart = LocalDateTime.of(firstDay, LocalTime.MIN).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            //最后一天23点59分59秒
            Long todayEnd = LocalDateTime.of(lastTheMonthDay, LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli();
            Map<String,Object> map =  new HashMap<>();
            map.put("firstStemp",todayStart);
            map.put("lastStemp",todayEnd);
            stampList.add(map);
        }
        return stampList;
    }

    private List<Integer> getCountByType(List<Map<String,Object>> stampList,String type,Set<Long> deptIds){
        List<Integer> countList = new ArrayList<>();
        for (Map<String, Object> map : stampList) {
            QueryWrapper<IllegalReport> wrapper = new QueryWrapper<>();
            wrapper.eq("type",type);
            // 大于开始时间
            wrapper.ge("create_time",(Long)map.get("firstStemp"));
            // 小于结束时间
            wrapper.lt("create_time",(Long)map.get("lastStemp"));
            wrapper.in("jurisdiction",deptIds);
            countList.add(this.count(wrapper));
        }
        return countList;
    }


    /**
     * 是否受理类别
     *
     * @param type
     * @return
     */
    private String getState(String type) {
        // 是否受理 0:未受理，1：已受理 2：无需受理 3：无效举报
        if (type != null && !"".equals(type)) {
            switch (type) {
                case "0":
                    return "未受理";
                case "1":
                    return "已受理";
                case "2":
                    return "无需受理";
                case "3":
                    return "无效举报";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 转换成时间戳
     *
     * @param date
     * @return
     */
    private String getTime(String date) {
        String time = "";
        // 2019-05-07T16:00:00.000Z
        if (date != null && !"".equals(date)) {
            date = date.split("T")[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                time = String.valueOf(sdf.parse(date).getTime() + 86400000L);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    /**
     * 获得类别
     *
     * @param path
     * @return
     */
    private String getType(String path) {
        String result = "";
        if (path != null && !"".equals(path)) {
            String postfix = path.substring(path.lastIndexOf(".") + 1);
            System.out.println(path.substring(path.lastIndexOf(".")));
            if ("mp4".equals(postfix)) {
                result = "ILLEGAL_REPORT_VIDEO";
            }
            if ("jpg".equals(postfix) || "png".equals(postfix)) {
                result = "ILLEGAL_REPORT_IMG";
            }
            if ("mp3".equals(postfix)) {
                result = "ILLEGAL_REPORT_MP3";
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> getStatisticsByType(Long startTime, Long endTime, List<String> depts, String dateType) {
        Map<String, Object> result = new HashMap<>();

        List<String> xAxis = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        List<Serie> series = new ArrayList<>();

        legend.add("未受理");
        legend.add("已受理");
        legend.add("无需受理");
        legend.add("无效举报");

        List<String> data1 = new ArrayList<>();
        Serie serie1 = new Serie();
        List<String> data2 = new ArrayList<>();
        Serie serie2 = new Serie();
        List<String> data3 = new ArrayList<>();
        Serie serie3 = new Serie();
        List<String> data4 = new ArrayList<>();
        Serie serie4 = new Serie();


        List<IllegalStatisticsDTO> list = baseMapper.getStatisticsByType(startTime, endTime, depts, dateType);
        String curr_time = "";
        for (IllegalStatisticsDTO illegal : list) {
            if (StrUtil.isEmpty(curr_time)) {
                curr_time = illegal.getTime();
                xAxis.add(curr_time);
            }
            if (!curr_time.equals(illegal.getTime())) {
                curr_time = illegal.getTime();
                xAxis.add(curr_time);
            }

            if ("0".equals(illegal.getType())) {
                // 未受理
                serie1.setType("bar");
                serie1.setName(legend.get(0));
                data1.add(illegal.getCnt());
                serie1.setData(data1);
            } else if ("1".equals(illegal.getType())) {
                // 已受理
                serie2.setType("bar");
                serie2.setName(legend.get(1));
                data2.add(illegal.getCnt());
                serie2.setData(data2);
            } else if ("2".equals(illegal.getType())) {
                // 无需受理
                serie3.setType("bar");
                serie3.setName(legend.get(2));
                data3.add(illegal.getCnt());
                serie3.setData(data3);
            } else if ("3".equals(illegal.getType())) {
                // 无效举报
                serie4.setType("bar");
                serie4.setName(legend.get(3));
                data4.add(illegal.getCnt());
                serie4.setData(data4);
            }
        }
        series.add(serie1);
        series.add(serie2);
        series.add(serie3);
        series.add(serie4);

        result.put("legend", legend);
        result.put("series", series);
        result.put("xAxis", xAxis);

        return result;
    }

    @Override
    public Map<String, Object> getDealStatisticsByType(Long startTime, Long endTime, List<String> depts, String dateType) {
        Map<String, Object> result = new HashMap<>();

        List<String> xAxis = new ArrayList<>();
        List<String> legend = new ArrayList<>();
        List<Serie> series = new ArrayList<>();

        legend.add("平均受理时间");
        legend.add("最慢受理时间");
        legend.add("最快受理时间");

        List<String> data1 = new ArrayList<>();
        Serie serie1 = new Serie();
        List<String> data2 = new ArrayList<>();
        Serie serie2 = new Serie();
        List<String> data3 = new ArrayList<>();
        Serie serie3 = new Serie();

        List<IllegalStatisticsDTO> list = baseMapper.getDealStatisticsByType(startTime, endTime, depts, dateType);

        long avgTime = 0L;
        for (IllegalStatisticsDTO illegal : list) {
            xAxis.add(illegal.getTime());

            // 平均受理时间
            serie1.setType("line");
            serie1.setName(legend.get(0));
            if (illegal.getAvgTime() != null) {
                avgTime = (long) Double.parseDouble(illegal.getAvgTime());
                data1.add(this.longTimeToHour(avgTime));
            } else {
                data1.add("0");
            }
            serie1.setData(data1);

            // 最慢受理时间
            serie2.setType("line");
            serie2.setName(legend.get(1));
            if (illegal.getMaxDealTime() != null) {
                avgTime = (long) Double.parseDouble(illegal.getMaxDealTime());
                data2.add(this.longTimeToHour(avgTime));
            } else {
                data2.add("0");
            }
            serie2.setData(data2);

            // 最快受理时间
            serie3.setType("line");
            serie3.setName(legend.get(2));
            if (illegal.getMinDealTime() != null) {
                avgTime = (long) Double.parseDouble(illegal.getMinDealTime());
                data3.add(this.longTimeToHour(avgTime));
            } else {
                data3.add("0");
            }
            serie3.setData(data3);
        }
        series.add(serie1);
        series.add(serie2);
        series.add(serie3);

        result.put("legend", legend);
        result.put("series", series);
        result.put("xAxis", xAxis);

        return result;
    }
    private String longTimeToHour(Long ms) {
        float hour = ms / (60 * 60 * 1000);
        return String.valueOf(hour);
    }
}
