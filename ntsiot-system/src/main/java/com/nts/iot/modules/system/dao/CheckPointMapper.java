package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.IconDto;
import com.nts.iot.modules.system.model.CheckPoint;
import com.nts.iot.modules.system.model.DictDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CheckPointMapper extends BaseMapper<CheckPoint> {

    Page<CheckPoint> selectByPage(Page<CheckPoint> page, @Param("name") String name, @Param("jurisdiction") List<Long> jurisdiction);

    /**
     * 获得所有必到点信息
     *
     * @return
     */
    List<CheckPoint> selectAll();

    /**
     * 查询图标
     */
    List<IconDto> selectIcon(@Param("jurisdiction") Long jurisdiction);


    /**
     * 查询图标
     */
    List<DictDetail> selectTasKType();
}
