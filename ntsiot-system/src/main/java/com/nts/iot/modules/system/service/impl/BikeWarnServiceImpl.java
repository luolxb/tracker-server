package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.BikeWarnMapper;
import com.nts.iot.modules.system.dto.BikeWarnDTO;
import com.nts.iot.modules.system.model.BikeWarn;
import com.nts.iot.modules.system.service.BikeWarnService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
@Service
public class BikeWarnServiceImpl extends ServiceImpl<BikeWarnMapper, BikeWarn> implements BikeWarnService {

    @Override
    public Map queryAll(BikeWarnDTO bikeWarnDTO, Set<Long> deptIds, Pageable pageable) {
        Page<BikeWarn> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        //按time降序排列
        page.setDesc("time");
        QueryWrapper<BikeWarn> wrapper = new QueryWrapper<>();
        wrapper.in("dept_id", deptIds);
        if (StrUtil.isNotEmpty(bikeWarnDTO.getBikeBarcode())) {
            wrapper.like("bike_barcode", bikeWarnDTO.getBikeBarcode());
        }
        if (StrUtil.isNotEmpty(bikeWarnDTO.getLockBarcode()))  {
            wrapper.like("lock_barcode", bikeWarnDTO.getLockBarcode());
        }
        if (Objects.nonNull(bikeWarnDTO.getStartTime()))  {
            wrapper.gt("time", bikeWarnDTO.getStartTime());
        }
        if (Objects.nonNull(bikeWarnDTO.getEndTime()))  {
            wrapper.lt("time", bikeWarnDTO.getEndTime());
        }
        IPage<BikeWarn> pageResult = baseMapper.selectPage(page, wrapper);
        return PageUtil.toPage(pageResult);
    }
}
