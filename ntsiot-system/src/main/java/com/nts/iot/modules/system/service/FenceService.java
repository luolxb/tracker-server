package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.Fence;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.model.vo.FenceStatisticRq;
import com.nts.iot.modules.system.model.vo.FenceStatisticVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author zhc@rnstec.com
 * @Description 限行围栏管理
 * @Date 2019-05-05 10:12
 * @Version: 1.0
 */
public interface FenceService extends IService<Fence> {

    Map queryAll(String name, String type, Long userId, Pageable pageable);

//    List<Fence> selectListByUserId(Long jurisdiction);

    /**
     * 初始化围栏
     */
    void initFences();

    void create(Fence fence, User user);

    void updateFence(Fence fence);

    void deleteFence(List<Long> ids);

    Fence getById(Long deviceId, Long fenceId);

    /**
     * 围栏报表
     *
     * @param fenceStatisticRq
     * @return
     */
    Page<FenceStatisticVo> fenceStatistic(FenceStatisticRq fenceStatisticRq, Pageable pageable);
}
