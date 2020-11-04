package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.BikeLicense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhc@rnstec.com
 * @Description 车辆授权mapper
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Repository
@Mapper
public interface BikeLicenseMapper extends BaseMapper<BikeLicense> {
    Page<BikeLicense> selectByPage(Page<BikeLicense> page, @Param("name") String name, @Param("telephone") String telephone,  @Param("jurisdictions") List<String> jurisdictions);

    List<BikeLicense> selectAll(@Param("telephone") String telephone,  @Param("jurisdiction") String jurisdiction);

}

