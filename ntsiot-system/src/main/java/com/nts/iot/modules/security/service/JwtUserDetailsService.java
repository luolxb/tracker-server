package com.nts.iot.modules.security.service;

import cn.hutool.core.date.DateUtil;
import com.nts.iot.constant.RedisKey;
import com.nts.iot.config.DataScope;
import com.nts.iot.modules.system.model.*;
import com.nts.iot.modules.system.service.*;
import com.nts.iot.exception.EntityNotFoundException;
import com.nts.iot.modules.miniApp.util.ToolUtil;
import com.nts.iot.modules.security.security.JwtUser;

import com.nts.iot.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author jie
 * @date 2018-11-22
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private JobService jobService;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private JurisdictionConfigurationService jurisdictionConfigurationService;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private JwtPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        JwtUser jwtUser;
        User user = userService.findUserByName(username);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", username);
        } else {
            Long userId = user.getId();

            List<Role> roles = roleService.findByUserId(userId);

//            Dept dept = deptService.getById(user.getDeptId());
//            Job job = jobService.getById(user.getJobId());
//            JurisdictionConfiguration deptConfig = jurisdictionConfigurationService.getJurisdictionConfiguration(user.getDeptId());
//            user.setJob(job);
//            user.setDept(dept);
//            user.setDeptConfig(deptConfig);
            user.setRoles(new HashSet<>(roles));

            Collection<SimpleGrantedAuthority> authorities = permissionService.mapToGrantedAuthorities(user);
            jwtUser = createJwtUser(user, authorities);

            return jwtUser;
        }
    }

    public JwtUser createJwtUser(User user, Collection<SimpleGrantedAuthority> authorities) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhone(),
                authorities,
                user.getEnabled(),
                user.getCreateTime(),
                user.getLastPasswordResetTime(),
                user.getCustomTypeId()
        );
    }

    private static Date getQueryTime(Long offSet, String time) {
        if (!ToolUtil.isOneEmpty(offSet, time)) {
            // 日期 yyyy-MM-dd
            String rq = DateUtil.formatDate(DateUtil.offsetDay(new Date(), offSet.intValue()));
            String queryTime = rq + " " + time;
            return DateUtil.parse(queryTime);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        DateUtil.offsetDay(new Date(), -7);
        System.out.println(DateUtil.offsetDay(new Date(), -7));
        System.out.println(DateUtil.formatDate(DateUtil.offsetDay(new Date(), -7)));
    }
}
