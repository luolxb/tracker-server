package com.nts.iot.modules.miniApp.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzm
 * @since 2018-03-05
 */
@Mapper
public interface RechargeRecordMapper extends BaseMapper<RechargeRecord> {
    /**
     * 查询
     *
     * @param page 分页
     * @param userName     金额
     * @return
     */
    Page<RechargeRecord> queryAll(Page<RechargeRecord> page, @Param("userName") String userName, @Param("jurisdiction") Long jurisdiction);
}
