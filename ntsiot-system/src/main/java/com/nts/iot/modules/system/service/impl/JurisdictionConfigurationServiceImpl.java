package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dto.DeptDto;
import com.nts.iot.modules.system.dao.JurisdictionConfigurationMapper;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.JurisdictionConfiguration;
import com.nts.iot.modules.system.service.JurisdictionConfigurationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class JurisdictionConfigurationServiceImpl extends ServiceImpl<JurisdictionConfigurationMapper, JurisdictionConfiguration> implements JurisdictionConfigurationService {

    @Override
    public JurisdictionConfiguration getJurisdictionConfiguration(Long jurisdiction) {
        JurisdictionConfiguration jurisdictionConfiguration = baseMapper.getJurisdictionConfiguration(jurisdiction);
        if (jurisdictionConfiguration == null) {
            jurisdictionConfiguration = baseMapper.getJurisdictionName(jurisdiction);
        }
        return jurisdictionConfiguration;
    }

    @Override
    public DeptDto getDeptInfoById(Dept dept) {
        // 通过dept id 查询辖区配置表
        QueryWrapper<JurisdictionConfiguration> jWrapper = new QueryWrapper<>();
        jWrapper.in("jurisdiction",dept.getId());
        JurisdictionConfiguration deptConfig = this.getOne(jWrapper,false);
        DeptDto deptDto = new DeptDto();
        // 设置dept id
        deptDto.setId(dept.getId());
        if (deptConfig!=null){
            deptDto.setBikeIcon(dept.getBikeIcon());
            deptDto.setLogo(deptConfig.getLogoUrl());
            // 如果获取不到辖区配置表的中的名称，则从辖区表中设置名称
            if (deptConfig.getName()!=null){
                deptDto.setName(deptConfig.getName());
            }else {
                deptDto.setName(dept.getName());
            }
            deptDto.setLatitude(deptConfig.getLatitude());
            deptDto.setLongitude(deptConfig.getLongitude());
            deptDto.setDeptPhone(deptConfig.getPhone());

            // yyyy-mm-dd
            LocalDate localDate = LocalDate.now();
            String startyyyymmdd;
            String endyyyymmdd;
            if (deptConfig.getStartTime()!=null && deptConfig.getEndTime()!=null){
                // 前几天
                if (deptConfig.getOtherDay()!=null){
                    LocalDate startDate =  localDate.plusDays(deptConfig.getOtherDay());
                    startyyyymmdd = startDate.toString();
                }else {
                    LocalDate startDate =  localDate.minusDays(1);
                    startyyyymmdd = startDate.toString();
                }
                // 后几天
                if (deptConfig.getNextDay()!=null){
                    LocalDate startDate =  localDate.plusDays(deptConfig.getNextDay());
                    endyyyymmdd = startDate.toString();
                }else {
                    LocalDate startDate =  localDate.minusDays(1);
                    endyyyymmdd = startDate.toString();
                }
                // yyyy-mm-dd hh:mm:ss
                String startStr = startyyyymmdd + " " + deptConfig.getStartTime();
                String endStr = endyyyymmdd + " " + deptConfig.getEndTime();
                Date startData = DateUtil.parse(startStr, "yyyy-MM-dd HH:mm:ss");
                Date endData = DateUtil.parse(endStr, "yyyy-MM-dd HH:mm:ss");
                // 默认开始时间
                deptDto.setStartTime(startData.getTime());
                // 默认结束时间
                deptDto.setEndTime(endData.getTime());
            }else {
                // 默认开始时间
                deptDto.setStartTime(null);
                // 默认结束时间
                deptDto.setEndTime(null);
            }
        }else {
            deptDto.setName(dept.getName());
        }
        if (dept.getPid() != null){
            QueryWrapper<JurisdictionConfiguration> pWrapper = new QueryWrapper<>();
            pWrapper.in("jurisdiction",dept.getPid());
            JurisdictionConfiguration parentDept = this.getOne(pWrapper,false);
            if (parentDept!=null){
                // 市警察局名称
                deptDto.setpName(parentDept.getName());
                // 市警察局电话
                deptDto.setCityPhone(parentDept.getPhone());
            }
        }
        return deptDto;
    }
}
