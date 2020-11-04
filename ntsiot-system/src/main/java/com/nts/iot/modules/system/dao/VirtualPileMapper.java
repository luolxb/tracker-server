package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.model.VirtualPile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zhc@rnstec.com
 * @Description 虚拟装mapper
 * @Date 2019-05-05 10:40
 * @Version: 1.0
 */
@Repository
@Mapper
public interface VirtualPileMapper extends BaseMapper<VirtualPile> {

    Page<VirtualPile> selectByPage(Page<VirtualPile> page, @Param("name") String name, @Param("jurisdictions") List<String> jurisdictions);

    List<VirtualPile> initPiles();
}
