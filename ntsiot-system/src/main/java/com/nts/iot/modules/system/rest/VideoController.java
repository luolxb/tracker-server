package com.nts.iot.modules.system.rest;

import java.util.ArrayList;
import java.util.List;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.Video;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;

/**
 * 视频
 * 
 * @author 蜜瓜
 *
 *         2019年11月12日
 */
@RequestMapping("api")
@RestController
public class VideoController extends JwtBaseController {

	@Autowired
	private VideoService videoService;
	@Autowired
	private DeptService deptService;

	/**
	 * 列表查询
	 * 
	 * @return
	 */
	@Log("视频列表查询")
	@GetMapping(value = "/video")
	@PreAuthorize("hasAnyRole('ADMIN','VIDEO_ALL','VIDEO_DELETE')")
	public ResponseEntity list(String title, Long startTime, Long endTime, Pageable pageable, @ModelAttribute("user") User user) {
		
		List<Long> depts = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            depts.add(dept.getId());
        }
		return new ResponseEntity(videoService.queryAll(title, startTime, endTime,depts, pageable), HttpStatus.OK);
	}

	/**
	 * 新增视频
	 * 
	 * @return
	 */
	@Log("新增视频")
	@PostMapping(value = "/video/add")
	@PreAuthorize("hasAnyRole('ADMIN','VIDEO_ALL','VIDEO_CREATE')")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity create(@RequestBody Video video, @ModelAttribute("user") User user) {
		video.setCreateTime(System.currentTimeMillis());
		return new ResponseEntity(videoService.save(video), HttpStatus.CREATED);
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@Log("视频修改")
	@PutMapping(value = "/video/update")
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ADMIN','VIDEO_ALL','VIDEO_EDIT')")
	public ResponseEntity update(@RequestBody Video video) {
		video.setUpdateTime(System.currentTimeMillis());

		return new ResponseEntity(videoService.updateById(video), HttpStatus.NO_CONTENT);
	}

	/**
	 * 删除视频
	 * 
	 * @param id
	 * @return
	 */
	@Log("视频删除")
	@DeleteMapping(value = "/video/{id}")
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAnyRole('ADMIN','VIDEO_ALL','VIDEO_DELETE')")
	public ResponseEntity delete(@PathVariable Long id) {
		return new ResponseEntity(videoService.removeById(id), HttpStatus.OK);
	}

}
