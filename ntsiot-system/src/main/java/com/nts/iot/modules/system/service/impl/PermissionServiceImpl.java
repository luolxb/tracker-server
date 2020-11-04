package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.exception.EntityExistException;
import com.nts.iot.modules.system.dao.PermissionMapper;
import com.nts.iot.modules.system.dto.PermissionDTO;
import com.nts.iot.modules.system.model.Permission;
import com.nts.iot.modules.system.model.Role;
import com.nts.iot.modules.system.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author jie
 * @date 2018-12-03
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public List<PermissionDTO> queryAll(String name) {
        return baseMapper.queryAll(name);
    }

    @Override
    public PermissionDTO findById(long id) {
//        Optional<Permission> permission = permissionRepository.findById(id);
//        ValidationUtil.isNull(permission,"Permission","id",id);
//        return permissionMapper.toDto(permission.get());
        PermissionDTO permissionDTO = new PermissionDTO();
        Permission permission = baseMapper.selectById(id);
        BeanUtil.copyProperties(permission, permissionDTO);
        return permissionDTO;

    }

    /**
     * findOneByName
     * @param resources
     * @return
     */
    Permission findOneByName(Permission resources){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(resources.getName())) {
            queryWrapper.eq("name", resources.getName());
        }
        return baseMapper.selectOne(queryWrapper);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer create(Permission resources) {
//        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
//        if (StrUtil.isNotEmpty(resources.getName())) {
//            queryWrapper.eq("name", resources.getName());
//        }
        if(findOneByName(resources) != null){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }
//        if(baseMapper.findByName(resources.getName()) != null){
//            throw new EntityExistException(Permission.class,"name",resources.getName());
//        }
//        return permissionMapper.toDto(baseMapper.save(resources));
        return baseMapper.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Permission resources) {
        Permission permission = baseMapper.selectById(resources.getId());
        Permission permission1 = this.findOneByName(resources);

        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        if(permission1 != null && !permission1.getId().equals(permission.getId())){
            throw new EntityExistException(Permission.class,"name",resources.getName());
        }

        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
//        baseMapper.save(permission);
        baseMapper.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //获取当前节点的子节点
        Map<String, Object> map = new HashMap<>();
        map.put("pid", id);
        List<Permission> permissionList = baseMapper.selectByMap(map);
        for (Permission permission : permissionList) {
            //删除子节点
            baseMapper.deleteById(permission);
        }
        //删除父节点
        baseMapper.deleteById(id);
    }

    @Override
    public Object getPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
                    if (permission!=null){
                        Map<String, Object> pMap = new HashMap<>();
                        pMap.put("pid", permission.getId());
                        List<Permission> permissionList = baseMapper.selectByMap(pMap);
//                      List<Permission> permissionList = permissionRepository.findByPid(permission.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",permission.getId());
                        map.put("label",permission.getAlias());
                        if(permissionList!=null && permissionList.size()!=0){
                            map.put("children",getPermissionTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Permission> findByPid(long pid) {
//        return permissionRepository.findByPid(pid);
        Map<String, Object> pMap = new HashMap<>();
        pMap.put("pid", pid);
        return baseMapper.selectByMap(pMap);
    }

    @Override
    public Object buildTree(List<PermissionDTO> permissionDTOS) {

        List<PermissionDTO> trees = new ArrayList<PermissionDTO>();

        for (PermissionDTO permissionDTO : permissionDTOS) {

            if ("0".equals(permissionDTO.getPid().toString())) {
                trees.add(permissionDTO);
            }

            for (PermissionDTO it : permissionDTOS) {
                if (it.getPid().equals(permissionDTO.getId())) {
                    if (permissionDTO.getChildren() == null) {
                        permissionDTO.setChildren(new ArrayList<PermissionDTO>());
                    }
                    permissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = permissionDTOS!=null?permissionDTOS.size():0;

        Map map = new HashMap();
        map.put("content",trees.size() == 0?permissionDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }

    @Override
    public Set<Permission> findByRoles(List<Role> roles) {
        Set<Permission> permissions = new HashSet<>(baseMapper.findByRoles(roles));
        return permissions;
    }
}
