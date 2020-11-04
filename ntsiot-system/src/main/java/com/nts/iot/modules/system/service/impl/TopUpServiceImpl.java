package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.TopUpMapper;
import com.nts.iot.modules.system.model.TopUp;
import com.nts.iot.modules.system.service.TopUpService;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TopUpServiceImpl extends ServiceImpl<TopUpMapper, TopUp> implements TopUpService {

    /**
     * 查询所有
     *
     * @param money
     * @param pageable
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String money, String startTime, String endTime, Long jurisdiction) {
        Page<TopUp> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<TopUp> pageResult = baseMapper.queryAll(page, money, getTime(startTime), getTime(endTime), jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    /**
     * 根据辖区查询金额
     *
     * @param jurisdiction
     * @return
     */
    @Override
    public List<String> queryMoneyByJurisdiction(Long jurisdiction) {
        return baseMapper.queryMoneyByJurisdiction(jurisdiction);
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
}