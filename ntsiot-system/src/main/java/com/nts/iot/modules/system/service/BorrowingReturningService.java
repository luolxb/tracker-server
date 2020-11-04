package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.dto.BorrowingReturningDto;
import com.nts.iot.modules.system.model.BorrowingReturning;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface BorrowingReturningService extends IService<BorrowingReturning> {
    /**
     * 查询所有必到点分页
     *
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable, String username, List<Long> jurisdiction);

    BorrowingReturningDto getBorrowingReturningDto(Long id, Long jurisdiction);

    BorrowingReturning getBorrowingReturningInfo(String openId, Long articleId);

    List<BorrowingReturning> getBorrowingReturningDtoCount(Long articleId, String openId);

    Object detailBorrowingReturning(Pageable pageable, String id, Long jurisdiction);

    void exportLoanCount(HttpServletResponse res, String path, String username, Long jurisdiction);

    List<String> addBorrowingReturning(BorrowingReturning borrowingReturning);
}
