package com.nts.iot.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.constant.RedisKey;
import com.google.common.collect.Lists;
import com.nts.iot.exception.BadRequestException;
import com.nts.iot.modules.system.dao.DeptMapper;
import com.nts.iot.modules.system.dto.DeptDTO;
import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Role;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.AppModuleService;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.util.JsonUtil;
import com.nts.iot.utils.PageUtil;
import com.nts.iot.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author jie
 * @date 2019-03-25
 */
@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AppModuleService appModuleService;

    @Override
    public List<DeptDTO> queryAll(DeptDTO dept, Set<Long> deptIds) {
        return baseMapper.queryAll(dept, deptIds);
    }

    @Override
    public DeptDTO findById(Long id) {
        DeptDTO deptDTO = new DeptDTO();
        Dept dept = baseMapper.selectById(id);
        BeanUtil.copyProperties(dept, deptDTO);
        return deptDTO;
    }

    @Override
    public List<Dept> findByPid(long pid) {
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        return baseMapper.selectByMap(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer create(Dept resources) {
        resources.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Dept parent = baseMapper.selectById(resources.getPid());
        String code = IdUtil.objectId();
        String number = parent.getNumber() + "-" + parent.getCode();
        resources.setCode(code);
        resources.setNumber(number);
        Integer result = baseMapper.insert(resources);
        this.getDeptAll();
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Dept parent = baseMapper.selectById(resources.getPid());
        if (parent != null) {
            String number = parent.getNumber() + "-" + parent.getCode();
            resources.setNumber(number);
        }
        baseMapper.updateById(resources);
        this.getDeptAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public Map buildTree(List<DeptDTO> deptDTOS) {
        Set<DeptDTO> trees = new LinkedHashSet<>();
        Set<DeptDTO> depts = new LinkedHashSet<>();
        List<Long> a = new ArrayList<>();
        deptDTOS.forEach(w -> a.add(w.getId()));
        Boolean isChild;
        for (DeptDTO deptDTO : deptDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (DeptDTO it : deptDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    if (!a.contains(deptDTO.getPid())) {
                        isChild = true;
                    }
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<DeptDTO>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDTOS != null ? deptDTOS.size() : 0;

        Map map = new HashMap();
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? deptDTOS : trees);
        return map;
    }

    @Override
    public Set<Dept> findByRoles(List<Role> roles) {
        Set<Dept> depts = new HashSet<>(baseMapper.findByRoles(roles));
        return depts;
    }

    @Override
    public List<Dept> findByCode(String code) {
        return baseMapper.findByCode(code);
    }

    @Override
    public List<Dept> findByUserRole(User user) {
        Set<Role> roles = user.getRoles();
        boolean flag = false;
        // 根据角色来进行区分，如果是超级管理员那么她可以看到所有的辖区的数据，如果只是辖区的管理员，那么他只能看见辖区的数据
        Iterator<Role> iterable = roles.iterator();
        while (iterable.hasNext()) {
            Role role = iterable.next();
            // 是否是超级管理员
            if (role.getId() == 1) {
                flag = true;
                break;
            }
        }

        List<Dept> depts;
        if (flag) {
            depts = this.list();
        } else {
            depts = this.findByCode(null);
        }
        return depts;
    }

    @Override
    public void getDeptAll() {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        List<Dept> deptList = this.list(queryWrapper);
        if (deptList != null && deptList.size() > 0) {
            for (Dept dept : deptList) {
                redisUtil.addRedis(RedisKey.JURISDICTION_KEY + dept.getId(), JsonUtil.getJson(dept));
                if (dept.getAppId() != null) {
                    Map<String, Object> wxMaMap = new HashMap<>();
                    wxMaMap.put("deptId", dept.getId());
                    wxMaMap.put("appId", dept.getAppId());
                    wxMaMap.put("secret", dept.getSecret());
                    wxMaMap.put("token", dept.getToken());
                    wxMaMap.put("aesKey", dept.getAesKey());
                    redisUtil.addRedis(RedisKey.WX_MA_KEY + dept.getAppId(), JsonUtil.getJson(wxMaMap));
                }
            }
            redisUtil.addRedis(RedisKey.LIST_JURISDICTION_KEY, JsonUtil.getJson(deptList));
        }
    }

    @Override
    public List<Long> getSubDepts(long deptId) {
        List<Long> deptIds = Lists.newArrayList();
        deptIds.add(deptId);
        List<Dept> subDepts = findByPid(deptId);
        if (!CollectionUtils.isEmpty(subDepts)) {
            for (Dept dept : subDepts) {
                //过滤禁用部门
                if (dept.getEnabled()) {
                    deptIds.addAll(getSubDepts(dept.getId()));
                }
            }
        }
        return deptIds;
    }

    @Override
    public List<Dept> getPidDepts(long deptId) {
        List<Dept> deptIds = Lists.newArrayList();
        boolean a = true;
        while (a) {
            Dept subDepts = baseMapper.selectById(deptId);
            if (null != subDepts) {
                //过滤禁用部门
                if (subDepts.getEnabled()) {
                    deptIds.add(subDepts);
                    deptId = subDepts.getPid();
                }
            } else {
                a = false;
            }
        }
        return deptIds;
    }

    @Override
    public Map getDeptList(String name, List<String> jurisdictions, Pageable pageable) {
        Page<Dept> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Dept> pageResult = baseMapper.selectByPage(page, name, jurisdictions);
        pageResult.getRecords().forEach(dept -> {
            // 根据辖区关联小程序模块
            dept.setAppModules(appModuleService.getListByDeptId(dept.getId()));
        });
        return PageUtil.toPage(pageResult);
    }

    @Override
    public List<Dept> findAllDepts() {
        return baseMapper.selectList(null);
    }

    @Override
    public List<Dept> getDeptAllAndJurisdictionConfig(List<Long> deptIds) {
        return baseMapper.getDeptAllAndJurisdictionConfig(deptIds);
    }
}