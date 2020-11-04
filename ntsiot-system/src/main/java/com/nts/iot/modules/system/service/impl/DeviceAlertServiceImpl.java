package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.miniApp.dto.CollectMessage;
import com.nts.iot.modules.miniApp.util.DateUtils;
import com.nts.iot.modules.system.dao.DeviceAlertMapper;
import com.nts.iot.modules.system.dao.DeviceRealAlertMapper;
import com.nts.iot.modules.system.model.DeviceAlert;
import com.nts.iot.modules.system.model.DeviceRealAlert;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.DeviceAlertProportionVo;
import com.nts.iot.modules.system.model.vo.DeviceAlertStatisticsVo;
import com.nts.iot.modules.system.model.vo.DeviceAlertTypeVo;
import com.nts.iot.modules.system.service.DeviceAlertService;
import com.nts.iot.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.nts.iot.constant.ConstantClass.switchlanguage;

/**
 * 设备报警服务类
 *
 * @PackageName: com.nts.iot.modules.system.service.impl
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-08 13:14
 **/

@Service
@Slf4j
public class DeviceAlertServiceImpl extends ServiceImpl<DeviceAlertMapper, DeviceAlert> implements DeviceAlertService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeviceRealAlertMapper deviceRealAlertMapper;

    @Value("${track.proxy.url}")
    private String trackProxyUrl;

    /**
     * 报警比例分析图
     *
     * @param user
     * @return
     */
    @Override
    public DeviceAlertProportionVo proportion(User user) {
        Map<String, Integer> map = deviceRealAlertMapper.proportion(user);
        return JsonUtil.jsonConvertObject(JsonUtil.getJson(map), DeviceAlertProportionVo.class);
    }

    @Override
    public List<Map<String, Object>> monitor(User user) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        //过去七天
//        c.setTime(new Date());
//        c.add(Calendar.DATE, -6);
//        Date d = c.getTime();
//        String startDate = format.format(d);
        String startDate = DeviceServiceImpl.getTime(-7);
        String endDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        List<Map<String, Object>> mapList = this.baseMapper.monitor(user, startDate, endDate);
        // 空集合 用于装新数据
        List<Map<String, Object>> newMapList = new ArrayList<>();
        // 如果返回值为空，赋值0
        if (CollectionUtils.isEmpty(mapList)) {
            for (int i = 0; i < 7; i++) {
                Map<String, Object> map = new HashMap<>();
                Calendar calendar = Calendar.getInstance();
                Date parse = format.parse(startDate);
                calendar.setTime(parse);
                calendar.add(Calendar.DATE, i);
                Date time = calendar.getTime();
                String date1 = format.format(time);
                map.put("date", date1);
                map.put("alertSum", 0);

                newMapList.add(map);
            }
        } else {
            // 拼接日期不连续，如果日期不连续赋值0
            // 遍历 获取到数据
            for (int j = 0; j < mapList.size(); j++) {
                // 计算最后一条数据到现在
                if (StringUtils.equals((String) mapList.get(j).get("date"), (String) mapList.get(mapList.size() - 1).get("date"))) {
                    Long monthNum = getDayNum(format.parse(startDate), new Date());
                    if (monthNum <= 0) {
                        newMapList.add(mapList.get(j));
                        continue;
                    }
                    for (int i = 0; i < monthNum + 1; i++) {
                        Map<String, Object> map = new HashMap<>();
                        Calendar calendar = Calendar.getInstance();
                        Date parse = format.parse(startDate);
                        calendar.setTime(parse);
                        calendar.add(Calendar.DATE, i);
                        Date time = calendar.getTime();
                        String date1 = format.format(time);
                        if (StringUtils.equals(date1, (String) mapList.get(mapList.size() - 1).get("date"))) {
                            map.put("alertSum", mapList.get(mapList.size() - 1).get("alertSum"));
                        } else {
                            map.put("alertSum", 0);
                        }
                        map.put("date", date1);
                        newMapList.add(map);
                    }
                    break;
                }

                String date = (String) mapList.get(j).get("date");
                Long monthNum = getDayNum(format.parse(startDate), format.parse(date));

                for (int i = 0; i < monthNum; i++) {
                    Map<String, Object> map = new HashMap<>();
                    Calendar calendar = Calendar.getInstance();
                    Date parse = format.parse(startDate);
                    calendar.setTime(parse);
                    calendar.add(Calendar.DATE, i);
                    Date time = calendar.getTime();
                    String date1 = format.format(time);
                    map.put("date", date1);
                    map.put("alertSum", 0);

                    newMapList.add(map);
                }
                newMapList.add(mapList.get(j));

                Calendar calendar = Calendar.getInstance();
                Date parse = format.parse((String) mapList.get(j).get("date"));
                calendar.setTime(parse);
                calendar.add(Calendar.DATE, 1);
                Date time = calendar.getTime();
                startDate = format.format(time);
            }
        }
        return newMapList;
    }

    /**
     * 新增报警信息
     *
     * @param collectMessages
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<CollectMessage> collectMessages, User user) {
        if (!CollectionUtils.isEmpty(collectMessages)) {
            List<DeviceAlert> deviceAlertList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            collectMessages
                    .stream()
                    // 过滤掉deviceNo为空的
                    .filter(collectMessage -> StringUtils.isNotBlank(collectMessage.getDeviceNo()))
                    .forEach(collectMessage -> {
                        DeviceAlert deviceAlert = new DeviceAlert();
                        deviceAlert.setCreateTime(new Date());
                        deviceAlert.setCreateBy(user != null ? user.getUsername() : "admin");
                        deviceAlert.setDeviceNo(collectMessage.getDeviceNo());
                        // 出围栏报警
                        deviceAlert.setAlertType(collectMessage.getAlertType());
                        deviceAlert.setLatitude(collectMessage.getLatitude());
                        deviceAlert.setLongitude(collectMessage.getLongitude());
                        try {
                            deviceAlert.setTime(dateFormat.parse(collectMessage.getTime()));
                        } catch (ParseException e) {
                            log.error("新增报警信息时间解析错误", e);
                            e.printStackTrace();
                        }
                        deviceAlert.setSpeed(collectMessage.getSpeed());
                        deviceAlert.setCourse(collectMessage.getCourse());
                        deviceAlertList.add(deviceAlert);
                    });
            try {
                boolean b = this.saveBatch(deviceAlertList);
                if (!b) {
                    log.error("新增报警失败");
                }
            } catch (Exception e) {
                log.error("新增报警失败", e);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceAlert> queryPage(Pageable pageable, Long userId, String deviceNo, String startDate, String endDate, Integer status) {
        Page<DeviceAlert> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(startDate)) {
            startDate = dateFormat.format(Long.parseLong(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            endDate = dateFormat.format(Long.parseLong(endDate));
        }
        List<DeviceAlert> deviceAlertList = this.baseMapper.selectDeviceAlertPage(page, status, userId, deviceNo, startDate, endDate);
        deviceAlertList.forEach(deviceAlert -> {
            // 速度转换为千米
//            if (StringUtils.isNotBlank(deviceAlert.getSpeed())) {
//                BigDecimal bg = new BigDecimal(Double.parseDouble(deviceAlert.getSpeed()) / 1000);
//                double doubleValue = bg.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
//                deviceAlert.setSpeed(String.valueOf(doubleValue));
//            }
        });
        page.setRecords(deviceAlertList);
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeviceAlert> queryPageDetail(Pageable pageable, Long userId, String deviceNo, String startDate, String endDate) {
        Page<DeviceAlert> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(startDate)) {
            startDate = dateFormat.format(Long.parseLong(startDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            endDate = dateFormat.format(Long.parseLong(endDate));
        }
        List<DeviceAlert> deviceAlertList = this.baseMapper.selectDeviceAlertPage(page, null, userId, deviceNo, startDate, endDate);
        deviceAlertList.forEach(deviceAlert -> {
            // 速度转换为千米
//            if (StringUtils.isNotBlank(deviceAlert.getSpeed())) {
//                BigDecimal bg = new BigDecimal(Double.parseDouble(deviceAlert.getSpeed()) / 1000);
//                double doubleValue = bg.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
//                deviceAlert.setSpeed(String.valueOf(doubleValue));
//            }
            if (StringUtils.isNotBlank(deviceAlert.getLatitude()) &&
                    StringUtils.isNotBlank(deviceAlert.getLongitude())) {
                String addr = "";
                try {
                    ResponseEntity<String> forEntity = restTemplate.getForEntity(trackProxyUrl + "/geocode/getBaiDuMap?language=" + switchlanguage +
                            "&lat=" + Double.parseDouble(deviceAlert.getLatitude()) + "&lon=" + Double.parseDouble(deviceAlert.getLongitude()), String.class);
                    addr = forEntity.getBody();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                deviceAlert.setAddr(addr == null ? "" : addr);
            }
        });
        page.setRecords(deviceAlertList);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeviceAlert(List<DeviceAlert> deviceAlertList, User user) {
        if (CollectionUtils.isEmpty(deviceAlertList)) {
            throw new BadRequestException("操作失败设备报警内容不能为空,The content of the device alarm cannot be empty if the operation fails");
        }
        deviceAlertList.forEach(deviceAlert -> {
            DeviceAlert alert = new DeviceAlert()
                    .setId(deviceAlert.getId())
                    .setStatus(2)
                    .setUpdateTime(new Date())
                    .setUpdateBy(user.getUsername());
            int i = this.baseMapper.updateById(alert);
            if (i <= 0) {
                throw new BadRequestException("修改失败,fail to edit");
            }
        });
    }

    /**
     * 报警统计【统计报表】
     *
     * @param userId
     * @param deviceNo
     * @return
     */
    @Override
    public List<DeviceAlertStatisticsVo> statistics(Long userId,
                                                    String deviceNo,
                                                    String startDate,
                                                    String endDate) {
        List<DeviceAlertStatisticsVo> deviceAlertStatisticsVos = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(startDate)) {
            startDate = simpleDateFormat.format(Long.parseLong(startDate));
        }

        if (StringUtils.isNotBlank(endDate)) {
            endDate = simpleDateFormat.format(Long.parseLong(endDate));
        }

        List<DeviceAlertTypeVo> statistics = this.baseMapper.statistics(userId, deviceNo, startDate, endDate);
        Map<String, List<DeviceAlertTypeVo>> collect = statistics.stream().collect(Collectors.groupingBy(DeviceAlertTypeVo::getDeviceNo));
        Set<String> str = collect.keySet();
        for (String s : str) {
            int alert_type_001 = 0;
            int alert_type_002 = 0;
            int alert_type_003 = 0;
            int alert_type_004 = 0;
            int alert_type_005 = 0;
            int alert_type_006 = 0;
            int alert_type_007 = 0;
            int alert_type_008 = 0;
            int alert_type_009 = 0;
            int alert_type_0010 = 0;
            int alert_type_0011 = 0;
            int alert_type_0012 = 0;
            List<DeviceAlertTypeVo> deviceAlertTypeVos = collect.get(s);
            DeviceAlertStatisticsVo deviceAlertStatisticsVo = new DeviceAlertStatisticsVo();
            deviceAlertStatisticsVo.setDeviceNo(s);
            for (DeviceAlertTypeVo d : deviceAlertTypeVos) {
                deviceAlertStatisticsVo.setDeviceName(d.getDeviceName());
                switch (d.getAlertType()) {
                    case "alert_type_001":
                        alert_type_001 += d.getNum();
                        break;
                    case "alert_type_002":
                        alert_type_002 += d.getNum();
                        break;
                    case "alert_type_003":
                        alert_type_003 += d.getNum();
                        break;
                    case "alert_type_004":
                        alert_type_004 += d.getNum();
                        break;
                    case "alert_type_005":
                        alert_type_005 += d.getNum();
                        break;
                    case "alert_type_006":
                        alert_type_006 += d.getNum();
                        break;
                    case "alert_type_007":
                        alert_type_007 += d.getNum();
                        break;
                    case "alert_type_008":
                        alert_type_008 += d.getNum();
                        break;
                    case "alert_type_009":
                        alert_type_009 += d.getNum();
                        break;
                    case "alert_type_0010":
                        alert_type_0010 += d.getNum();
                        break;
                    case "alert_type_0011":
                        alert_type_0011 += d.getNum();
                        break;
                    case "alert_type_0012":
                        alert_type_0012 += d.getNum();
                        break;
                }
            }
            deviceAlertStatisticsVo.setAlert_type_001(alert_type_001);
            deviceAlertStatisticsVo.setAlert_type_002(alert_type_002);
            deviceAlertStatisticsVo.setAlert_type_003(alert_type_003);
            deviceAlertStatisticsVo.setAlert_type_004(alert_type_004);
            deviceAlertStatisticsVo.setAlert_type_005(alert_type_005);
            deviceAlertStatisticsVo.setAlert_type_006(alert_type_006);
            deviceAlertStatisticsVo.setAlert_type_007(alert_type_007);
            deviceAlertStatisticsVo.setAlert_type_008(alert_type_008);
            deviceAlertStatisticsVo.setAlert_type_009(alert_type_009);
            deviceAlertStatisticsVo.setAlert_type_0010(alert_type_0010);
            deviceAlertStatisticsVo.setAlert_type_0011(alert_type_0011);
            deviceAlertStatisticsVo.setAlert_type_0012(alert_type_0012);
            deviceAlertStatisticsVos.add(deviceAlertStatisticsVo);
        }
        return deviceAlertStatisticsVos;
    }

    /**
     * 两个时间间隔天数
     *
     * @param start
     * @param end
     * @return
     */
    private static Long getDayNum(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        long nd = 1000 * 24 * 60 * 60;
        return diff / nd;
    }


}
