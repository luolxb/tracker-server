package com.nts.iot.modules.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.VideoMapper;
import com.nts.iot.modules.system.model.Video;
import com.nts.iot.modules.system.service.VideoService;
import com.nts.iot.utils.PageUtil;

import cn.hutool.core.util.StrUtil;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

	@Override
	public Map queryAll(String title, Long startTime, Long endTime, List<Long> depts, Pageable pageable) {
		Page<Video> page = new Page<Video>(pageable.getPageNumber() + 1, pageable.getPageSize());
		page.setDesc("create_time");
		QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
		if (StrUtil.isNotEmpty(title)) {
			queryWrapper.like("title", title);
		}
		if (depts != null && depts.size() > 0) {
			queryWrapper.in("dept_id", depts);
		}
		if (startTime != null) {
			queryWrapper.ge("create_time", startTime);
		}
		if (endTime != null) {
			queryWrapper.le("create_time", endTime);
		}
		IPage<Video> pageResult = baseMapper.selectPage(page, queryWrapper);
		return PageUtil.toPage(pageResult);
	}
}
