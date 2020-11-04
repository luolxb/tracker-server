package com.nts.iot.modules.miniApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.dto.IllegalStatisticsDTO;
import com.nts.iot.modules.miniApp.model.IllegalReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IllegalReportMapper extends BaseMapper<IllegalReport> {

    Page<IllegalReport> selectByPage(Page page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdiction") List<Long> jurisdiction);

    List<IllegalReport> selectIllegalReport(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdiction") Long jurisdiction);

    List<IllegalStatisticsDTO> getStatisticsByType(@Param("startTime") Long startTime, @Param("endTime") Long endTime,
                                                   @Param("depts") List<String> depts, @Param("dateType") String dateType);

    List<IllegalStatisticsDTO> getDealStatisticsByType(@Param("startTime") Long startTime, @Param("endTime") Long endTime,
                                                   @Param("depts") List<String> depts, @Param("dateType") String dateType);

}
