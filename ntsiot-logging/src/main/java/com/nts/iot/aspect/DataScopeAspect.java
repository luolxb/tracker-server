package com.nts.iot.aspect;

import com.nts.iot.model.LogMybatis;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.service.LogService;
import com.nts.iot.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jie
 * @date 2018-11-24
 */
@Component
@Aspect
@Slf4j
public class DataScopeAspect {

    @Autowired
    private LogService logService;

    private long currentTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.nts.iot.aop.log.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        Object result = null;
        currentTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("e",e);
            throw new BadRequestException(e.getMessage());
        }
//        LogMybatis log = new LogMybatis("INFO",System.currentTimeMillis() - currentTime);
        LogMybatis log = new LogMybatis();
        log.setLogType("INFO");
        log.setTime(System.currentTimeMillis() - currentTime);
        logService.save(joinPoint, log);

        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
//        LogMybatis log = new LogMybatis("ERROR",System.currentTimeMillis() - currentTime);
        LogMybatis log = new LogMybatis();
        log.setLogType("ERROR");
        log.setTime(System.currentTimeMillis() - currentTime);
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
        logService.save((ProceedingJoinPoint)joinPoint, log);
    }
}
