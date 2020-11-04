package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.modules.system.dao.VirtualPileMapper;
import com.nts.iot.modules.system.model.VirtualPile;
import com.nts.iot.modules.system.service.VirtualPileService;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author zhc@rnstec.com
 * @Description
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Service
public class VirtualPileServiceImpl extends ServiceImpl<VirtualPileMapper, VirtualPile> implements VirtualPileService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map queryAll(String name, List<String> jurisdictions, Pageable pageable) {
        Page<VirtualPile> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<VirtualPile> pageResult = baseMapper.selectByPage(page, name, jurisdictions);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public void initPiles() {
        List<VirtualPile> piles = baseMapper.initPiles();
        Long curr_jurisdiction = null;
        List<VirtualPile> list = new ArrayList<>();
        for (VirtualPile pile : piles) {
            if(curr_jurisdiction == null){
                curr_jurisdiction = pile.getJurisdiction();
            }
            if (curr_jurisdiction != pile.getJurisdiction()){
                String key = RedisKey.VIRTUAL_PILE + curr_jurisdiction;
                redisUtil.addRedis(key, JSON.toJSONString(list));

                //重置
                curr_jurisdiction = null;
                list.clear();
            }
            list.add(pile);
        }
        String key = RedisKey.VIRTUAL_PILE + curr_jurisdiction;
        redisUtil.addRedis(key, JSON.toJSONString(list));
    }
}
