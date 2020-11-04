package com.nts.iot.modules.miniApp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.miniApp.dto.MaUserDTO;
import com.nts.iot.modules.miniApp.model.MaUser;
import com.nts.iot.modules.system.model.TaskPatrolman;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author jie
 * @date 2018-11-23
 */
public interface MaUserService extends IService<MaUser> {

    Map queryAll(MaUserDTO userDTO, Set<Long> deptIds, Pageable pageable);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    List<TaskPatrolman> queryByDeptId(Long deptId);

    MaUser getUserByWxId(String openId);


}
