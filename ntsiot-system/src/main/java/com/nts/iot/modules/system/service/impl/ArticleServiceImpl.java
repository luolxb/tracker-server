package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.ArticleMapper;
import com.nts.iot.modules.system.dto.LoanCountDto;
import com.nts.iot.modules.system.model.Article;
import com.nts.iot.modules.system.model.ArticleNumber;
import com.nts.iot.modules.system.model.BorrowingReturning;
import com.nts.iot.modules.system.service.ArticleNumberService;
import com.nts.iot.modules.system.service.ArticleService;
import com.nts.iot.modules.system.service.BorrowingReturningService;
import com.nts.iot.modules.system.service.MessageService;
import com.nts.iot.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private BorrowingReturningService borrowingReturningService;

    @Autowired
    private ArticleNumberService articleNumberService;

    @Autowired
    private MessageService messageService;

    /**
     * 查询
     *
     * @param name
     * @param pageable
     * @return
     */
    @Override
    public Object queryAll(Pageable pageable, String name, List<Long> jurisdiction) {
        Page<Article> page = new Page(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Article> pageResult = baseMapper.selectByPage(page, name, jurisdiction);
        return PageUtil.toPage(pageResult);
    }

    @Override
    public List<Article> selectArticle(Long jurisdiction) {
        return baseMapper.selectArticle(jurisdiction);
    }

    @Override
    public List<BorrowingReturning> selectBorrowingReturning(Long jurisdiction, String openId) {
        List<BorrowingReturning> borrowingReturningList = baseMapper.selectBorrowingReturning(openId);
        if (borrowingReturningList != null && borrowingReturningList.size() > 0) {
            for (int i = 0; i < borrowingReturningList.size(); i++) {
                BorrowingReturning borrowingReturning = borrowingReturningList.get(i);
                List<LoanCountDto> loanCountDtoList = baseMapper.selectLoanCount(borrowingReturning.getId(), jurisdiction);
                borrowingReturning.setLoanCountDtoList(loanCountDtoList);
            }
        }
        return borrowingReturningList;
    }

    @Override
    public List<BorrowingReturning> selectGive(Long jurisdiction, String openId) {
        List<BorrowingReturning> br = new ArrayList<>();
        List<BorrowingReturning> borrowingReturningList = baseMapper.selectNotGive(openId);
        if (borrowingReturningList != null && borrowingReturningList.size() > 0) {
            for (int i = 0; i < borrowingReturningList.size(); i++) {
                BorrowingReturning borrowingReturning = borrowingReturningList.get(i);
                List<LoanCountDto> loanCountDtoList = baseMapper.selectNotGiveCount(borrowingReturning.getId(), jurisdiction);
                if (loanCountDtoList != null && loanCountDtoList.size() > 0) {
                    borrowingReturning.setLoanCountDtoList(loanCountDtoList);
                    br.add(borrowingReturning);
                }
            }
        }
        return br;
    }

    @Override
    public void updateGive(String code,String userId) {
        String[] array = code.split(";");
        Long pk = null;
        for (String s : array) {
            LoanCountDto loanCountDto = baseMapper.selectGiveNumber(Long.parseLong(s.split(",")[0]));
            pk = loanCountDto.getBorrowingReturningId();
            int num = 0;
            if (loanCountDto.getGiveNumber() != null && !"".equals(loanCountDto.getGiveNumber())) {
                num = Integer.parseInt(loanCountDto.getGiveNumber());
            }
            baseMapper.updateGive(String.valueOf(Integer.parseInt(s.split(",")[1]) + num), Long.parseLong(s.split(",")[0]), System.currentTimeMillis());
            Long id = baseMapper.selectLoanCById(Long.parseLong(s.split(",")[0]));
            if (id != null) {
                // 归还数量
                int count = Integer.parseInt(s.split(",")[1]);
                // 修改数量表
                ArticleNumber articleNumber = articleNumberService.getById(id);
                if (articleNumber != null) {
                    Integer nums = Integer.parseInt(articleNumber.getLoanNumber()) - count;
                    articleNumber.setLoanNumber(String.valueOf(nums));
                    articleNumberService.updateById(articleNumber);
                }
            }
        }
        if (pk != null) {
            int count = baseMapper.selectLcCount(pk);
            int giveCount = baseMapper.selectGiveCount(pk);
            if (count == giveCount) {
                BorrowingReturning borrowingReturning = borrowingReturningService.getById(pk);
                borrowingReturning.setRepayTime(System.currentTimeMillis());
                borrowingReturningService.updateById(borrowingReturning);
            }
        }
        // 发送归还成功消息
        messageService.sendMsg(Long.valueOf(userId),"give",null,"归还成功");
    }

    @Override
    public void saveLoanCount(Long borrowingReturningId, String articleId, String number, String giveNumber) {
        baseMapper.saveLoanCount(borrowingReturningId, articleId, number, giveNumber);
    }

    @Override
    public boolean checkBorrowingReturning(Long borrowingReturningId) {
        boolean flag = false;
        if (baseMapper.selectLCByBorrowingReturningId(borrowingReturningId) == baseMapper.selectLCByBRId(borrowingReturningId)) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void delLCByBorrowingReturningId(Long borrowingReturningId) {
        baseMapper.delLCByBorrowingReturningId(borrowingReturningId);
    }

    @Override
    public List<LoanCountDto> selectLoanC(Long borrowingReturningId) {
        return baseMapper.selectLoanC(borrowingReturningId);
    }
}
