package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.RankByBike;
import com.nts.iot.modules.miniApp.model.RankByUser;
import com.nts.iot.modules.system.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhc@rnstec.com
 * @Description 订单管理mapper
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Repository
@Mapper
public interface OrderManagerMapper extends BaseMapper<Order> {

    Page<Order> selectByPage(Page<Order> page, @Param("order") Order order, @Param("jurisdictions") List<String> jurisdictions);

    List<RankByUser> rankByJurisdiction(@Param("jurisdictions") List<Long> jurisdictions, @Param("startTime")Long startTime, @Param("endTime")Long endTime);

    List<RankByUser> rankByAllUser(@Param("jurisdictions") List<Long> jurisdictions);

    List<RankByUser> rankByAllUserPc(@Param("jurisdictions") List<Long> jurisdictions,@Param("startTime")Long startTime,@Param("endTime")Long endTime);

    List<RankByUser> rankByJurisdictionPc(Page<RankByUser> page,@Param("jurisdictions") List<Long> jurisdictions,@Param("startTime")Long startTime,@Param("endTime")Long endTime);

    List<RankByBike> rankByBikeJurisdictionPc(Page<RankByBike> page, @Param("jurisdictions") List<Long> jurisdictions, @Param("startTime")Long startTime, @Param("endTime")Long endTime);
}
