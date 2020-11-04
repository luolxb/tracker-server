package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.system.dto.DictDetailDTO;
import com.nts.iot.modules.system.model.ArticleNumber;
import com.nts.iot.modules.system.model.BorrowingReturning;
import com.nts.iot.modules.system.service.ArticleNumberService;
import com.nts.iot.modules.system.service.ArticleService;
import com.nts.iot.modules.system.service.BorrowingReturningService;
import com.nts.iot.modules.system.service.DictDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("ma")
public class MaBorrowingReturningController {


    @Autowired
    private DictDetailService dictDetailService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleNumberService articleNumberService;

    @Autowired
    private BorrowingReturningService borrowingReturningService;




    @GetMapping(value = "/dictDetail")
    public ResponseEntity getDictDetails(DictDetailDTO resources, Pageable pageable) {
        return new ResponseEntity(dictDetailService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 获得指定辖区的物品
     *
     * @return
     */
    @GetMapping(value = "/getArticle")
    public ResponseEntity getArticle(String jurisdiction) {
        return new ResponseEntity(articleService.selectArticle(Long.parseLong(jurisdiction)), HttpStatus.OK);
    }

    /**
     * 添加
     *
     * @param borrowingReturning
     * @return
     */
    @PostMapping(value = "/addBorrowingReturning")
    public List<String> addBorrowingReturning(@RequestBody BorrowingReturning borrowingReturning) {
        return borrowingReturningService.addBorrowingReturning(borrowingReturning);
    }


    /**
     * 获得指定辖区的物品
     *
     * @return
     */
    @GetMapping(value = "/selectBorrowingReturning")
    public ResponseEntity selectBorrowingReturning(String openId, String jurisdiction) {
        return new ResponseEntity(articleService.selectBorrowingReturning(Long.parseLong(jurisdiction), openId), HttpStatus.OK);
    }

    /**
     * 获得未归还物品
     *
     * @return
     */
    @GetMapping(value = "/selectGive")
    public ResponseEntity selectGive(String openId, String jurisdiction) {
        return new ResponseEntity(articleService.selectGive(Long.parseLong(jurisdiction), openId), HttpStatus.OK);
    }

    /**
     * 归还
     *
     * @return
     */
    @GetMapping(value = "/give")
    public ResponseEntity give(String code,String userId) {
        articleService.updateGive(code,userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 更新数量表
     *
     * @param articleId
     * @param count
     */
    private void updateArticleNumber(Long articleId, String count) {
        // 更改数量表
        ArticleNumber articleNumber = articleNumberService.selectByArticleNumberId(articleId);
        Long num = 0L;
        if (articleNumber != null && !"".equals(articleNumber.getLoanNumber())) {
            num = Long.parseLong(articleNumber.getLoanNumber());
        }

        Long number = Long.parseLong(articleNumber.getLoanNumber()) - Long.parseLong(count);
        articleNumber.setLoanNumber(String.valueOf(number));
        articleNumberService.updateById(articleNumber);
    }
}
