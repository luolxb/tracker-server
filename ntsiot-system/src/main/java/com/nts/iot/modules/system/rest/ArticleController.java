package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.system.model.Article;
import com.nts.iot.modules.system.model.ArticleNumber;
import com.nts.iot.modules.system.service.ArticleNumberService;
import com.nts.iot.modules.system.service.ArticleService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private DataScope dataScope;
    @Autowired
    private ArticleNumberService articleNumberService;

    private static final String ENTITY_NAME = "article";

    @Log("物品类别查询")
    @GetMapping(value = "/article")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_ALL','ARTICLE_SELECT')")
    public ResponseEntity getCheckPoint(Pageable pageable, @RequestParam(required = false) String name) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(articleService.queryAll(pageable, name, list), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param article
     * @return
     */
    @Log("物品类别新增")
    @PostMapping(value = "/article/add")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_ALL','ARTICLE_CREATE')")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity create(@Validated @RequestBody Article article) {
        if (article.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        article.setCreateTime(System.currentTimeMillis());
        Boolean flag = articleService.save(article);

        if (flag) {
            Long pk = article.getId();
            ArticleNumber articleNumber = new ArticleNumber();
            articleNumber.setTotal(article.getNumber());
            articleNumber.setLoanNumber("0");
            articleNumber.setCreateTime(System.currentTimeMillis());
            articleNumber.setArticleId(pk);
            articleNumberService.save(articleNumber);
        }
        return new ResponseEntity(flag, HttpStatus.CREATED);
    }

    /**
     * 修改
     *
     * @param article
     * @return
     */
    @Log("物品类别修改")
    @PutMapping(value = "/article/edit")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_ALL','ARTICLE_EDIT')")
    public ResponseEntity update(@RequestBody Article article) {
        Boolean flag = articleService.updateById(article);
        if (flag) {
            Long pk = article.getId();
            ArticleNumber articleNumber = articleNumberService.selectByArticleId(pk);
            if (articleNumber != null) {
                articleNumber.setTotal(article.getNumber());
                articleNumberService.updateById(articleNumber);
            }else{
                ArticleNumber articleN = new ArticleNumber();
                articleN.setTotal(article.getNumber());
                articleN.setLoanNumber("0");
                articleN.setCreateTime(System.currentTimeMillis());
                articleN.setArticleId(pk);
                articleNumberService.save(articleN);
            }
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Log("物品类别删除")
    @DeleteMapping(value = "/article/del/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ARTICLE_ALL','ARTICLE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        articleService.removeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获得指定辖区的物品
     *
     * @return
     */
    @GetMapping(value = "/getArticle")
    public ResponseEntity getArticle() {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
        if (list != null && list.size() > 0) {
            jurisdiction = list.get(0);
        }
        return new ResponseEntity(articleService.selectArticle(jurisdiction), HttpStatus.OK);
    }
}
