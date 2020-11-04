package com.nts.iot.modules.miniApp.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.InternalInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 内部消息上传
 */
@Mapper
@Repository
public interface InternalInformationMapper extends BaseMapper<InternalInformation> {


    Page<InternalInformation> selectByPage(Page page, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("jurisdiction") Long jurisdiction);
}

