package com.nts.iot.modules.miniApp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.miniApp.dao.MaUserMapper;
import com.nts.iot.modules.miniApp.dto.MaUserDTO;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.miniApp.service.MaUserService;
import com.nts.iot.modules.system.model.TaskPatrolman;
import com.nts.iot.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.nts.iot.constant.MiniAppConstants.USER_TYPE_INNER;

/**
 * @author jie
 * @date 2018-11-23
 */
@Service
public class MaUserServiceImpl extends ServiceImpl<MaUserMapper, MaUser> implements MaUserService {


    @Override
    public Map queryAll(MaUserDTO userDTO, Set<Long> deptIds, Pageable pageable) {
        Page<MaUser> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        QueryWrapper<MaUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_type",USER_TYPE_INNER);
        if (StrUtil.isNotEmpty(userDTO.getName())) {
            wrapper.like("name", userDTO.getName());
        }
        if (userDTO.getPhone() != null) {
            wrapper.eq("phone", userDTO.getPhone());
        }
        // 总辖区可以看所有
        if (deptIds.size() > 0 ) {
            wrapper.in("dept_id", deptIds);
        }
        IPage<MaUser> pageResult = baseMapper.selectPage(page, wrapper);

        return PageUtil.toPage(pageResult);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public List<TaskPatrolman> queryByDeptId(Long deptId) {
        List<TaskPatrolman> taskPatrolmen = new ArrayList<>();
        Map<String, Object> columnMap = new HashMap<>(2);
        columnMap.put("dept_id", deptId);
        // 内部人员
        columnMap.put("user_type", "0");

        List<MaUser> maUsers = baseMapper.selectByMap(columnMap);
        maUsers.forEach(it -> {
            TaskPatrolman taskPatrolman = new TaskPatrolman();
            taskPatrolman.setPatrolmanId(it.getId());
            taskPatrolman.setPatrolman(it.getName());
            taskPatrolman.setPhone(it.getPhone());
            taskPatrolman.setDeptId(it.getDeptId());
            taskPatrolmen.add(taskPatrolman);
        });
        return taskPatrolmen;
    }

    @Override
    public MaUser getUserByWxId(String openId) {
        if (openId == null) {
            return null;
        }
        // 通过unionId查询用户
        // 搜索是否已经存在该用户
        QueryWrapper<MaUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openId);
        queryWrapper.orderByDesc("create_time");
        return this.getOne(queryWrapper,false);
    }
}
