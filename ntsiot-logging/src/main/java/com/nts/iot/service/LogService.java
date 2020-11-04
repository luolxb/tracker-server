package com.nts.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.model.LogMybatis;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.text.ParseException;

/**
 * @author jie
 * @date 2018-11-24
 */
public interface LogService extends IService<LogMybatis> {

    /**
     * 新增日志
     * @param joinPoint
     * @param log
     */
    @Async
    void save(ProceedingJoinPoint joinPoint, LogMybatis log);

    Object queryAll(LogMybatis log, Pageable pageable);

    /**
     * 获取一个时间段的IP记录
     * @param date1
     * @param date2
     * @return
     */
    Long findIp(String date1, String date2);

    Object getUserLogsLogin(String username, String startDate, String endDate, Pageable pageable) throws ParseException;
}
