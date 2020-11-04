package com.nts.iot.modules.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nts.iot.modules.system.dao.NewDictMapper;
import com.nts.iot.modules.system.model.NewDict;
import com.nts.iot.modules.system.service.NewDictService;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PackageName: com.nts.iot.modules.system.service.impl
 * @program: nts
 * @author: ruosen
 * @create: 2020-05-07 15:11
 **/
@Service
@Slf4j
public class NewDictServiceImpl extends ServiceImpl<NewDictMapper, NewDict> implements NewDictService {

    private static final String REDIS_NEW_DICT_KEY = "REDIS_NEW_DICT_KEY ::";

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 查询全部数据字典信息
     *
     * @return
     */
    @Override
    public List<NewDict> newDict(Long pId) {
        QueryWrapper<NewDict> newDictQueryWrapper = new QueryWrapper<>();
        if (null != pId) {
            newDictQueryWrapper
                    .eq("id", pId)
                    .or()
                    .eq("p_id", pId);
        }
        newDictQueryWrapper.eq("enabled", 1);
        newDictQueryWrapper.eq("del_flag", 1);
        List<NewDict> newDicts = this.baseMapper.selectList(newDictQueryWrapper);
        List<NewDict> newDictRootList = newDicts
                .stream()
                .filter(newDict ->
                        !newDicts.stream().map(NewDict::getId).collect(Collectors.toList()).contains(newDict.getPId()))
                .collect(Collectors.toList());

        getChild(newDicts, newDictRootList);
        return newDictRootList;
    }

    private void getChild(List<NewDict> newDicts, List<NewDict> newDictRootList) {
        newDictRootList.forEach(newDictRoot -> {
            List<NewDict> list = newDicts
                    .stream()
                    .filter(newDict ->
                            newDict.getPId().equals(newDictRoot.getId()))
                    .collect(Collectors.toList());
            if (list.size() > 0) {
                newDictRoot.setNewDictChild(list);
                getChild(newDicts, list);
            }
        });
    }
}
