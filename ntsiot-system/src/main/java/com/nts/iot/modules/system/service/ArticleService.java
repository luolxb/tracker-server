package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.LoanCountDto;
import com.nts.iot.modules.system.model.Article;
import com.nts.iot.modules.system.model.BorrowingReturning;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService extends IService<Article> {
    Object queryAll(Pageable pageable, String name, List<Long> jurisdiction);

    List<Article> selectArticle(Long jurisdiction);

    List<BorrowingReturning> selectBorrowingReturning(Long jurisdiction, String openId);

    List<BorrowingReturning> selectGive(Long jurisdiction, String openId);

    void updateGive(String code,String userId);

    void saveLoanCount(Long borrowingReturningId, String articleId, String number,String giveNumber);

    boolean checkBorrowingReturning(Long borrowingReturningId);

    void delLCByBorrowingReturningId(Long borrowingReturningId);

    List<LoanCountDto> selectLoanC(Long borrowingReturningId);
}
