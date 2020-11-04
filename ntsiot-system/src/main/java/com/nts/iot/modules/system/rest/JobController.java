package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Job;
import com.nts.iot.modules.system.service.JobService;
import com.nts.iot.modules.system.service.UserService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jie
 * @date 2019-03-29
 */
@RestController
@RequestMapping("api")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private UserService userService;

    private static final String ENTITY_NAME = "job";

    @Log("查询岗位")
    @GetMapping(value = "/job")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_SELECT','USER_ALL','USER_SELECT')")
    public ResponseEntity getJobs(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) Long deptId,
                                  @RequestParam(required = false) Boolean enabled,
                                  Pageable pageable){
        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();

        return new ResponseEntity(jobService.queryAll(name, enabled , deptIds, deptId, pageable),HttpStatus.OK);
    }

    @Log("新增岗位")
    @PostMapping(value = "/job")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Job resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(jobService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改岗位")
    @PutMapping(value = "/job")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_EDIT')")
    public ResponseEntity update(@RequestBody Job resources){
        jobService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除岗位")
    @DeleteMapping(value = "/job/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        Map<String, Object> map = new HashMap<>();
        if (userService.findByJob(id) > 0) {
            map.put("isEmpty", false);
            return new ResponseEntity(map, HttpStatus.IM_USED);
        }
        jobService.delete(id);
        map.put("isEmpty", true);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}