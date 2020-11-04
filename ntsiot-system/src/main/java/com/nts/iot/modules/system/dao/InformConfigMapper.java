package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.InformConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/4 13:23
 * @Description:
 */
@Repository
@Mapper
public interface InformConfigMapper extends BaseMapper<InformConfig> {

    /**
     * 列表
     * @param page
     * @param title
     * @param jurisdictions
     * @return
     */
    Page<InformConfig> selectByPage(Page<InformConfig> page, @Param("title") String title, @Param("jurisdictions") List<String> jurisdictions);
}
