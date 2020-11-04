package com.nts.iot.modules.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.monitor.service.VisitsService;
import com.nts.iot.modules.monitor.dao.VisitMapper;
import com.nts.iot.service.LogService;
import com.nts.iot.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import com.nts.iot.modules.monitor.domain.Visits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jie
 * @date 2018-12-13
 */
@Slf4j
@Service
public class VisitsServiceImpl extends ServiceImpl<VisitMapper, Visits> implements VisitsService {


    @Autowired
    private LogService logService;

    @Override
    public void save() {
        LocalDate localDate = LocalDate.now();
        Visits visits = findByDate(localDate.toString());
        if(visits == null){
            visits = new Visits();
            visits.setWeekDay(StringUtils.getWeekDay());
            visits.setPvCounts(1L);
            visits.setIpCounts(1L);
            visits.setDate(localDate.toString());
            visits.setCreateTime(new Timestamp(System.currentTimeMillis()));
            //baseMapper.insert(visits);
        }
    }

    @Override
    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        Visits visits = findByDate(localDate.toString());
        visits.setPvCounts(visits.getPvCounts()+1);
        long ipCounts = logService.findIp(localDate.toString(), localDate.plusDays(1).toString());
//        long ipCounts = 1l;
        visits.setIpCounts(ipCounts);
        //baseMapper.updateById(visits);

    }

    @Override
    public Object get() {
        Map map = new HashMap();
        LocalDate localDate = LocalDate.now();
        Visits visits = findByDate(localDate.toString());
        List<Visits> list = this.findAllVisits(localDate.minusDays(6).toString(),localDate.plusDays(1).toString());

        long recentVisits = 0, recentIp = 0;
        for (Visits data : list) {
            recentVisits += data.getPvCounts();
            recentIp += data.getIpCounts();
        }
        map.put("newVisits",visits.getPvCounts());
        map.put("newIp",visits.getIpCounts());
        map.put("recentVisits",recentVisits);
        map.put("recentIp",recentIp);
        return map;
    }

    @Override
    public Object getChartData() {
        Map map = new HashMap();
        LocalDate localDate = LocalDate.now();
        List<Visits> list = this.findAllVisits(localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
        map.put("weekDays",list.stream().map(Visits::getWeekDay).collect(Collectors.toList()));
        map.put("visitsData",list.stream().map(Visits::getPvCounts).collect(Collectors.toList()));
        map.put("ipData",list.stream().map(Visits::getIpCounts).collect(Collectors.toList()));
        return map;
    }

    Visits findByDate(String date){
        QueryWrapper<Visits> wrapper = new QueryWrapper<>();
        wrapper.eq("date", date);
        //return baseMapper.selectOne(wrapper);
        return null;
    }

    List<Visits> findAllVisits(String startTime, String endTime){
        QueryWrapper<Visits> wrapper = new QueryWrapper<>();
        wrapper.between("create_time", startTime, endTime);
       // return baseMapper.selectList(wrapper);
        return null;
    }
}
