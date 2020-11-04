package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.system.service.CommonProblemService;
import com.nts.iot.modules.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/6 15:19
 * @Description:
 */
@RestController
@RequestMapping("ma")
public class MaCommonProblemController {
    @Autowired
    private CommonProblemService commonProblemService;

    @Autowired
    private DeptService deptService;

    /**
     * 获取列表
     * @param deptId
     * @return
     */
    @GetMapping("/problem/list")
    public ResponseEntity queryAll(Long deptId){
        return new ResponseEntity(commonProblemService.getProblemsByDeptId(deptId), HttpStatus.CREATED);
    }

    /**
     * 根据id查找常见问题
     * @param id
     * @return
     */
    @GetMapping("/problem/get")
    public ResponseEntity getProblemById(Long id){
        return new ResponseEntity(commonProblemService.getById(id), HttpStatus.CREATED);
    }
}
