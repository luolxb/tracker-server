package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.MileageStatisticsMapper;
import com.nts.iot.modules.system.model.MileageStatistics;
import com.nts.iot.modules.system.model.vo.MileageStatisticsVo;
import com.nts.iot.modules.system.service.MileageStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MileageStatisticsServiceServiceImpl extends ServiceImpl<MileageStatisticsMapper, MileageStatistics> implements MileageStatisticsService {

    public List<MileageStatisticsVo> queryAll(String deviceNo,String startDate,String endDate){


        return null;
    }


}
