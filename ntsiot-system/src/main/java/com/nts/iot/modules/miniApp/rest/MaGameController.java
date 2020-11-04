package com.nts.iot.modules.miniApp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.iot.modules.system.service.GameService;

/**
 * 小程序游戏模块
 * @author 蜜瓜
 *
 * 2019年11月12日
 */
@RestController
@RequestMapping("ma")
public class MaGameController {

	@Autowired
	private GameService gameService;

	/**
	 * 列表查询
	 * 
	 * @return
	 */
	@GetMapping(value = "/game")
	public ResponseEntity list(String name, Long startTime, Long endTime, Pageable pageable) {
		
		return new ResponseEntity(gameService.queryAll(name, startTime, endTime, pageable), HttpStatus.OK);
	}
}
