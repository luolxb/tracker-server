package com.nts.iot.modules.security.service;

import com.nts.iot.modules.system.model.Permission;
import com.nts.iot.modules.system.model.Role;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.PermissionService;
import com.nts.iot.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "role")
public class JwtPermissionService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Cacheable(key = "'loadPermissionByUser:' + #p0.username")
    public Collection<SimpleGrantedAuthority> mapToGrantedAuthorities(User user) {

        System.out.println("--------------------loadPermissionByUser:" + user.getUsername() + "---------------------");

//        Set<Role> roles = roleRepository.findByUsers_Id(user.getId());
        List<Role> roles = roleService.findByUserId(user.getId());

        Set<Permission> permissions = new HashSet<>();

//        permissions.addAll(permissionRepository.findByRoles(roles));
        permissions.addAll(permissionService.findByRoles(roles));

        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }
}
