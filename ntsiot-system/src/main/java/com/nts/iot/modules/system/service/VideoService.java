package com.nts.iot.modules.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.Video;

public interface VideoService extends IService<Video>{

	Map queryAll(String title, Long startTime, Long endTime, List<Long> depts,Pageable pageable);

}
