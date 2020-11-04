package com.nts.iot.modules.miniApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.miniApp.model.AccountHistory;
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
public interface AccountHistoryMapper extends BaseMapper<AccountHistory> {

    /**
     * 查询
     *
     * @param page 分页
     * @param userName     金额
     * @return
     */
    Page<AccountHistory> queryAll(Page<AccountHistory> page, @Param("userName") String userName, @Param("jurisdiction") Long jurisdiction);
}
