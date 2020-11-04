package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.service.RealCheckPointService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.system.dto.TaskDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实际任务关联的必到点
 *
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:34
 * @Description:
 */
@RestController
@RequestMapping("api")
public class RealCheckPointController extends JwtBaseController {

    @Autowired
    private RealCheckPointService realCheckPointService;

    /**
     * 查询任务详细
     *
     * @return
     */
    @Log("查看任务详细")
    @GetMapping(value = "/selectTaskDetail/{id}")
    public ResponseEntity selectTaskDetail(@PathVariable Long id) {
        List<TaskDetailDto> taskDetailDtoList = realCheckPointService.selectTaskDetail(id);
        return new ResponseEntity(taskDetailDtoList, HttpStatus.OK);
    }
}
