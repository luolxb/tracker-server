package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Fence;
import com.nts.iot.modules.system.model.vo.FenceStatisticRq;
import com.nts.iot.modules.system.model.vo.FenceStatisticVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Qualifier;
import java.util.List;

/**
 * @Author zhc@rnstec.com
 * @Description 限行围栏管理mapper
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Repository
@Mapper
public interface FenceMapper extends BaseMapper<Fence> {
    Page<Fence> selectByPage(Page<Fence> page,
                             @Param("name") String name,
                             @Param("type") String type,
                             @Param("userId") Long userId);

    List<Fence> selectListByJurisdiction(@Param("jurisdiction") Long jurisdiction);

    List<Fence> initFences();

    Fence selectById(@Param("deviceId") Long deviceId,
                     @Param("fenceId") Long fenceId);

    List<FenceStatisticVo> fenceStatistic(Page<FenceStatisticVo> page,@Param("fenceStatisticRq") FenceStatisticRq fenceStatisticRq);
}
