package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.GisServiceMapper;
import com.nts.iot.modules.system.model.GisService;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.GisServiceRq;
import com.nts.iot.modules.system.service.GisServiceService;
import com.nts.iot.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class GisServiceServiceImp extends ServiceImpl<GisServiceMapper, GisService> implements GisServiceService {

    /**
     * GIS 分页
     *
     * @param type
     * @param name
     * @param userId
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Map queryPage(String type, String name, Long userId, Pageable pageable) {
        Page<GisService> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        QueryWrapper<GisService> gisServiceQueryWrapper = new QueryWrapper<>();
        gisServiceQueryWrapper.eq("user_id", userId);
        if (StringUtils.isNotBlank(type)) {
            gisServiceQueryWrapper.eq("type", type);
        }
        if (StringUtils.isNotBlank(name)) {
            gisServiceQueryWrapper.like("name", name);
        }
        IPage<GisService> gisServiceIPage = this.baseMapper.selectPage(page, gisServiceQueryWrapper);
        return PageUtil.toPage(gisServiceIPage);
    }

    /**
     * 新增gis服务
     *
     * @param gisServiceRq
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(GisServiceRq gisServiceRq, User user) {
        GisService gisService = new GisService();
        BeanUtils.copyProperties(gisServiceRq, gisService);
        gisService.setCreateTime(new Date());
        gisService.setCreateBy(user.getUsername());
        this.save(gisService);
    }



    /**
     * 删除GIS服务
     * {"ids":[1,2]}
     * @param ids
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ids, User user) {
        JSONObject parse = (JSONObject) JSONObject.parse(ids);
        List<Integer> idsList = (List<Integer>) parse.get("ids");
        if (CollectionUtils.isEmpty(idsList)) {
            throw new BadRequestException("GIS服务不能为空,GIS service cannot be empty");
        }

        boolean b = this.removeByIds(idsList);
        if (!b) {
            throw new BadRequestException("删除GIS服务失败,Failed to delete GIS service");
        }
    }
}
