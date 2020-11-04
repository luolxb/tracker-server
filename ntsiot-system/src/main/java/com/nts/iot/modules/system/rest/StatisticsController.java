package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.modules.system.dto.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author:pengchenghe
 * @date: 2019-11-19
 * @time: 11:29
 */
@RestController
@RequestMapping("api")
public class StatisticsController {
    @Autowired
    private DeptService deptService;


    @Log("查询部门")
    @GetMapping(value = "statistics/dept")
    public ResponseEntity getDepts(String deptId) {
        // 数据权限
        deptId = "2";
        List<Long> deptIds = deptService.getSubDepts(Long.valueOf(deptId));
        DeptDTO resources=new DeptDTO();
        Set<Long> dept=new HashSet<>();
        deptIds.forEach(w->dept.add(w));
        List<DeptDTO> deptDTOS = deptService.queryAll(resources, dept);
        Map map=deptService.buildTree(deptDTOS);
        Dept d=deptService.getById(Long.valueOf(deptId));
        List<Dept> pdept=deptService.getPidDepts(d.getPid());
        map.put("Pdept",pdept);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
