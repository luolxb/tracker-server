package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.News;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.NewsService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:34
 * @Description:
 */
@RestController
@RequestMapping("api")
public class NewsController extends JwtBaseController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private DeptService deptService;

    /**
     * 列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("新闻公告查询")
    @GetMapping(value = "/news")
    @PreAuthorize("hasAnyRole('ADMIN','NEWS_ALL','NEWS_DELETE')")
    public ResponseEntity getList(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = new ArrayList<>();

        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }

        return new ResponseEntity(newsService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }


    /**
     * 新增新闻公告
     * @param news
     * @param user
     * @return
     */
    @Log("新增新闻公告")
    @PostMapping(value = "/news/add")
    @PreAuthorize("hasAnyRole('ADMIN','NEWS_ALL','NEWS_CREATE')")
    public ResponseEntity create(@Validated @RequestBody News news, @ModelAttribute("user") User user){
        // 辖区
        // 创建时间
        news.setCreateTime(System.currentTimeMillis());
        // 是否显示 默认显示
        news.setIsVisible("0");
        newsService.saveNew(news);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Log("删除新闻公告")
    @DeleteMapping(value = "/news/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NEWS_ALL','NEWS_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        newsService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 获取内容
     * @param id
     * @return
     */
    @Log("查看新闻公告")
    @GetMapping(value = "/news/get/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NEWS_ALL')")
    public ResponseEntity getNewById(@PathVariable Long id){
        return new ResponseEntity(newsService.getByNewId(id),HttpStatus.OK);
    }

    /**
     * 修改
     * @param news
     * @return
     */
    @Log("修改新闻公告")
    @PutMapping(value = "/news/edit")
    @PreAuthorize("hasAnyRole('ADMIN','NEWS_ALL','NEWS_EDIT')")
    public ResponseEntity update(@Validated @RequestBody News news){
        newsService.updateNewsById(news);
        return new ResponseEntity(HttpStatus.OK);
    }
}
