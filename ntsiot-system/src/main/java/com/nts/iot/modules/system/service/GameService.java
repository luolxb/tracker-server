package com.nts.iot.modules.system.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.Game;

public interface GameService extends IService<Game>{

	Map queryAll(String name, Long startTime, Long endTime, Pageable pageable);

}
