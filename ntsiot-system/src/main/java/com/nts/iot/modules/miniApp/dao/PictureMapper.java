package com.nts.iot.modules.miniApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nts.iot.modules.miniApp.model.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureMapper extends BaseMapper<Picture> {

    List<Picture> getPictureList(@Param("workDiaryId") Long workDiaryId, @Param("type") String type);
}
