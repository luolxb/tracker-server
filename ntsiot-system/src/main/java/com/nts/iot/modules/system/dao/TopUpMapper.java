package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.TopUp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TopUpMapper extends BaseMapper<TopUp> {

    /**
     * 查询
     *
     * @param page
     * @param money     金额
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    Page<TopUp> queryAll(Page<TopUp> page, @Param("money") String money, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdiction") Long jurisdiction);

    /**
     * 根据辖区查询金额
     *
     * @return
     */
    List<String> queryMoneyByJurisdiction(@Param("jurisdiction") Long jurisdiction);
}
