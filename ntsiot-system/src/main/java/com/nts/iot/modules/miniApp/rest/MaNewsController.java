package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 14:29
 * @Description:
 */
@RestController
@RequestMapping("ma")
public class MaNewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private DeptService deptService;

    /**
     * 获取列表
     * @param deptId
     * @return
     */
    @GetMapping("/news/list")
    public ResponseEntity queryAll(Long deptId){
        return new ResponseEntity(newsService.getNewsByDeptId(deptId), HttpStatus.CREATED);
    }

    /**
     * 获取内容
     * @param id
     * @return
     */
    @GetMapping(value = "/news/get")
    public ResponseEntity getNewById(Long id){
        return new ResponseEntity(newsService.getByNewId(id),HttpStatus.OK);
    }
}
