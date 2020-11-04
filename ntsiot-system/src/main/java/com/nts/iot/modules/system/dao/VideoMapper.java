package com.nts.iot.modules.system.dao;

import com.nts.iot.modules.system.model.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 
 * @author 蜜瓜
 *
 * 2019年11月12日
 */
@Repository
@Mapper
public interface VideoMapper extends BaseMapper<Video>{

}
