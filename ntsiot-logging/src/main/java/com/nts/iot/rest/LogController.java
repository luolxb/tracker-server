package com.nts.iot.rest;

import com.nts.iot.model.LogMybatis;
import com.nts.iot.service.LogService;
import com.nts.iot.utils.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author jie
 * @date 2018-11-24
 */
@RestController
@RequestMapping("api")
@Api(tags = "日志")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping(value = "/logs")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getLogs(LogMybatis log, Pageable pageable) {
        log.setLogType("INFO");
        return new ResponseEntity(logService.queryAll(log, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/user")
    public ResponseEntity getUserLogs(LogMybatis log, Pageable pageable) {
        log.setLogType("INFO");
        log.setUsername(SecurityContextHolder.getUserDetails().getUsername());
        return new ResponseEntity(logService.queryAll(log, pageable), HttpStatus.OK);
    }

    @ApiOperation("登陆日志")
    @GetMapping(value = "/logs/user/login")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "String", paramType = "query")
    })
    public Object getUserLogsLogin(@RequestParam("username") String username,
                                   String startDate,
                                   String endDate,
                                   Pageable pageable) throws ParseException {
        return logService.getUserLogsLogin(username, startDate, endDate, pageable);
    }

    @GetMapping(value = "/logs/error")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getErrorLogs(LogMybatis log, Pageable pageable) {
        log.setLogType("ERROR");
        return new ResponseEntity(logService.queryAll(log, pageable), HttpStatus.OK);
    }
}
