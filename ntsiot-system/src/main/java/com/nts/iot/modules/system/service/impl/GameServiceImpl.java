package com.nts.iot.modules.system.service.impl;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.GameMapper;
import com.nts.iot.modules.system.model.Game;
import com.nts.iot.modules.system.service.GameService;
import com.nts.iot.utils.PageUtil;

import cn.hutool.core.util.StrUtil;

@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {

	@Override
	public Map queryAll(String name, Long startTime, Long endTime, Pageable pageable) {
		Page<Game> page = new Page<Game>(pageable.getPageNumber() + 1, pageable.getPageSize());
		page.setAsc("num_order");
		QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
		if (StrUtil.isNotEmpty(name)) {
			queryWrapper.like("name", name);
		}
		if (startTime != null) {
			queryWrapper.ge("create_time", startTime);
		}
		if (endTime != null) {
			queryWrapper.le("create_time", endTime);
		}
		IPage<Game> pageResult = baseMapper.selectPage(page, queryWrapper);
		return PageUtil.toPage(pageResult);
	}

}
