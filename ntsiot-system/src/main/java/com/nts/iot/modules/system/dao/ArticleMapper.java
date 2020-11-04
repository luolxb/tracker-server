package com.nts.iot.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nts.iot.modules.system.dto.LoanCountDto;
import com.nts.iot.modules.system.model.Article;
import com.nts.iot.modules.system.model.BorrowingReturning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    Page<Article> selectByPage(Page<Article> page, @Param("name") String name, @Param("jurisdiction") List<Long> jurisdiction);

    List<Article> selectArticle(@Param("jurisdiction") Long jurisdiction);

    List<BorrowingReturning> selectBorrowingReturning(@Param("openId") String openId);

    List<LoanCountDto> selectLoanCount(@Param("borrowingReturningId") Long borrowingReturningId, @Param("jurisdiction") Long jurisdiction);

    List<BorrowingReturning> selectNotGive(@Param("openId") String openId);

    List<LoanCountDto> selectNotGiveCount(@Param("borrowingReturningId") Long borrowingReturningId, @Param("jurisdiction") Long jurisdiction);

    void updateGive(@Param("giveNumber") String giveNumber, @Param("id") Long id , @Param("giveTime") Long giveTime);

    int selectLcCount(@Param("borrowingReturningId") Long borrowingReturningId);

    int selectGiveCount(@Param("borrowingReturningId") Long borrowingReturningId);

    LoanCountDto selectGiveNumber(@Param("id") Long id);

    void saveLoanCount(@Param("borrowingReturningId") Long borrowingReturningId, @Param("articleId") String articleId, @Param("number") String number, @Param("giveNumber") String giveNumber);

    int selectLCByBorrowingReturningId(@Param("borrowingReturningId") Long borrowingReturningId);

    int selectLCByBRId(@Param("borrowingReturningId") Long borrowingReturningId);

    void delLCByBorrowingReturningId(@Param("borrowingReturningId") Long borrowingReturningId);

    List<LoanCountDto> selectLoanC(@Param("borrowingReturningId") Long borrowingReturningId);

    Long selectLoanCById(@Param("id") Long id);
}
