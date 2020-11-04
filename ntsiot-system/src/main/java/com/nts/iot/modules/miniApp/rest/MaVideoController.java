package com.nts.iot.modules.miniApp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.VideoService;

/**
 * 小程序视频模块
 *
 * @author 蜜瓜
 * <p>
 * 2019年11月12日
 */
@RestController
@RequestMapping("ma")
public class MaVideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     *
     * @return
     */
    @GetMapping(value = "/video")
    public ResponseEntity list(Long deptId, String title, Long startTime, Long endTime, Pageable pageable) {
        List<Long> depts = deptService.getSubDepts(deptId);
        return new ResponseEntity(videoService.queryAll(title, startTime, endTime, depts, pageable), HttpStatus.OK);
    }
}
