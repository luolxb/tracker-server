package com.nts.iot.modules.miniApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.WorkDiary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkDiaryMapper extends BaseMapper<WorkDiary> {

    Page<WorkDiary> selectByPage(Page page, @Param("jurisdictions")List<Long> jurisdictions,@Param("startTime") String startTime, @Param("endTime") String endTime);
}
