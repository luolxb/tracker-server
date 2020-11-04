package com.nts.iot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.aop.log.Log;
import com.nts.iot.model.LogMybatis;
import com.nts.iot.modules.log.dao.LogMapper;
import com.nts.iot.service.LogService;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RequestHolder;
import com.nts.iot.utils.SecurityContextHolder;
import com.nts.iot.utils.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jie
 * @date 2018-11-24
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogMybatis> implements LogService {

    private final String LOGINPATH = "login";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProceedingJoinPoint joinPoint, LogMybatis log){

        // 获取request
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);

        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        String params = "{";
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        // 用户名
        String username = "";

        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
        }

        // 获取IP地址
        log.setRequestIp(StringUtils.getIP(request));

        if(!LOGINPATH.equals(signature.getName())){
            UserDetails userDetails = SecurityContextHolder.getUserDetails();
            username = userDetails.getUsername();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(argValues[0]);
                username = jsonObject.get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params + " }");
        log.setCreateTime(DateUtil.date().toTimestamp());
        baseMapper.insert(log);
    }

    @Override
    public Object queryAll(LogMybatis log, Pageable pageable) {
        Page<LogMybatis> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        QueryWrapper<LogMybatis> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(log.getUsername())) {
            wrapper.like("username", log.getUsername());
        }
        if (StrUtil.isNotEmpty(log.getLogType())) {
            wrapper.eq("log_type", log.getLogType());
        }
        wrapper.orderBy(true, false, "create_time");
        IPage<LogMybatis> pageResult = baseMapper.selectPage(page, wrapper);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public Long findIp(String date1, String date2) {
        return 1L;
    }

    /**
     *
     * @param username  用户名
     * @param startDate  开始时间  时间戳
     * @param endDate  结束时间  时间戳
     * @param pageable  分页
     * @return
     * @throws ParseException  解析异常
     */
    @Override
    public Object getUserLogsLogin(String username, String startDate, String endDate, Pageable pageable) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Page<LogMybatis> page = new Page(pageable.getPageNumber(), pageable.getPageSize());
        QueryWrapper<LogMybatis> wrapper = new QueryWrapper<>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(username)) {
            wrapper.eq("username",username);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(startDate)) {
            String format = dateFormat.format(Long.parseLong(startDate));
            Date parse = dateFormat.parse(format);
            wrapper.ge("create_time", parse);
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(endDate)) {
            String format = dateFormat.format(Long.parseLong(endDate));
            Date parse = dateFormat.parse(format);
            wrapper.le("create_time", parse);
        }
        wrapper.eq("description", "用户登录,User login");
        wrapper.orderBy(true, false, "create_time");
        IPage<LogMybatis> pageResult = baseMapper.selectPage(page, wrapper);
        Map<String,Object> map =new HashMap<>();
        map.put("code",200);
        map.put("message","success");
        map.put("data",pageResult);
        return map;
    }
}
