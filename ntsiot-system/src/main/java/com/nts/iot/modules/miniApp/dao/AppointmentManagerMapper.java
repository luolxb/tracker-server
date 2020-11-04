package com.nts.iot.modules.miniApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.AppointmentManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppointmentManagerMapper extends BaseMapper<AppointmentManager> {
    Page<AppointmentManager> selectByPage(Page page, @Param("username") String username, @Param("jurisdiction") Long jurisdiction);

    List<AppointmentManager> selectByPeriod(@Param("period") Integer period);

    void add(@Param("username") String username,@Param("phone") String phone,@Param("jurisdiction") Long jurisdiction,
             @Param("switchFlag") Integer switchFlag,@Param("period") Integer period,@Param("createTime") Long createTime);

    void update(@Param("id") Long id,@Param("username") String username,@Param("phone") String phone,@Param("jurisdiction") Long jurisdiction,
             @Param("switchFlag") Integer switchFlag,@Param("period") Integer period,@Param("updateTime") Long updateTime);

    void delete(@Param("id") Long id);
}
