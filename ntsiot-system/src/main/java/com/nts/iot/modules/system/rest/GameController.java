package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Game;
import com.nts.iot.modules.system.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;

/**
 * 小游戏
 * 
 * @author 蜜瓜
 *
 *         2019年11月12日
 */
@RequestMapping("api")
@RestController
public class GameController extends JwtBaseController {
	@Autowired
	private GameService gameService;

	/**
	 * 列表查询
	 * 
	 * @return
	 */
	@Log("小游戏列表查询")
	@GetMapping(value = "/game")
	@PreAuthorize("hasAnyRole('ADMIN','GAME_ALL','GAME_DELETE')")
	public ResponseEntity list(String name, Long startTime, Long endTime, Pageable pageable) {
		
		return new ResponseEntity(gameService.queryAll(name, startTime, endTime, pageable), HttpStatus.OK);
	}

	/**
	 * 新增小游戏
	 * 
	 * @return
	 */
	@Log("新增小游戏")
	@PostMapping(value = "/game/add")
	@PreAuthorize("hasAnyRole('ADMIN','GAME_ALL','GAME_CREATE')")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity create(@Validated @RequestBody Game game) {
		game.setCreateTime(System.currentTimeMillis());
		return new ResponseEntity(gameService.save(game), HttpStatus.CREATED);
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@Log("小游戏修改")
	@PutMapping(value = "/game/update")
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ADMIN','GAME_ALL','GAME_EDIT')")
	public ResponseEntity update(@RequestBody Game game) {
		game.setUpdateTime(System.currentTimeMillis());

		return new ResponseEntity(gameService.updateById(game), HttpStatus.NO_CONTENT);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@Log("小游戏删除")
	@DeleteMapping(value = "/game/{id}")
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ADMIN','GAME_ALL','GAME_DELETE')")
	public ResponseEntity delete(@PathVariable Long id) {
		return new ResponseEntity(gameService.removeById(id), HttpStatus.OK);
	}

}
