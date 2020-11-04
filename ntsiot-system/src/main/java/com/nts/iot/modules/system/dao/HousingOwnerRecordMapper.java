package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.dto.IllegalStatisticsDTO;
import com.nts.iot.modules.system.dto.HouseRecordDTO;
import com.nts.iot.modules.system.model.HousingOwnerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/9/16 15:13
 * @Description:
 */
@Repository
@Mapper
public interface HousingOwnerRecordMapper extends BaseMapper<HousingOwnerRecord> {

    Page<HouseRecordDTO> selectByPage(Page<HouseRecordDTO> page, @Param("owner") String owner, @Param("phone") String phone, @Param("depts") List<String> depts);

    List<HouseRecordDTO> exportHousingRentalReport(@Param("owner") String owner, @Param("phone") String phone, @Param("depts") List<String> depts);

    List<IllegalStatisticsDTO> getStatisticsByType(@Param("startTime") Long startTime, @Param("endTime") Long endTime,
                                                   @Param("depts") List<String> depts, @Param("dateType") String dateType);
}
