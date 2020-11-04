package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.Bike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhc@rnstec.com
 * @Description 车辆管理mapper
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Repository
@Mapper
public interface BikeMapper extends BaseMapper<Bike> {

    Page<Bike> selectByPage(Page<Bike> page, @Param("bike") Bike bike, @Param("jurisdictions") List<String> jurisdictions);

    List<Bike> selectByPageApp(@Param("jurisdictions") List<String> jurisdictions);

}
