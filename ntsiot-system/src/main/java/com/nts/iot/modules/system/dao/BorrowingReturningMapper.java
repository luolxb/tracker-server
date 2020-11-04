package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.BorrowingReturningDto;
import com.nts.iot.modules.system.dto.LoanCountDto;
import com.nts.iot.modules.system.model.BorrowingReturning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface BorrowingReturningMapper extends BaseMapper<BorrowingReturning> {

    Page<BorrowingReturning> selectByPage(Page<BorrowingReturning> page, @Param("username") String username, @Param("jurisdiction") List<Long> jurisdiction);

    BorrowingReturningDto getBorrowingReturningDto(@Param("id") Long id, @Param("jurisdiction") Long jurisdiction);

    BorrowingReturning getBorrowingReturningInfo(@Param("openId") String openId, @Param("articleId") Long articleId);

    List<BorrowingReturning> getBorrowingReturningDtoCount(@Param("articleId") Long articleId, @Param("openId") String openId);

    Page<BorrowingReturning> detailBorrowingReturning(Page<BorrowingReturning> page, @Param("id") String id, @Param("jurisdiction") Long jurisdiction);

    List<LoanCountDto> exportLoanCount(@Param("username") String username, @Param("jurisdiction") Long jurisdiction);
}
