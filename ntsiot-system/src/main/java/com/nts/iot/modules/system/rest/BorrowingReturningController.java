package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.BorrowingReturning;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.BorrowingReturningService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class BorrowingReturningController extends JwtBaseController {

    @Autowired
    private BorrowingReturningService borrowingReturningService;

    @Autowired
    private DataScope dataScope;

    private static final String ENTITY_NAME = "borrowingReturning";

    /**
     * 查询
     *
     * @param pageable
     * @return
     */
    @Log("物品借还记录查询")
    @GetMapping(value = "/borrowingReturning")
    @PreAuthorize("hasAnyRole('ADMIN','BORROWING_RETURNING_ALL','BORROWING_RETURNING_SELECT')")
    public ResponseEntity getCheckPoint(Pageable pageable, @RequestParam(required = false) String username) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(borrowingReturningService.queryAll(pageable,username, list), HttpStatus.OK);
    }

    /**
     * 新增
     *
     * @param borrowingReturning
     * @return
     */
    @Log("新增物品借还记录")
    @PostMapping(value = "/borrowingReturning/add")
    @PreAuthorize("hasAnyRole('ADMIN','BORROWING_RETURNING_ALL','BORROWING_RETURNING_CREATE')")
    public ResponseEntity create(@Validated @RequestBody BorrowingReturning borrowingReturning, @ModelAttribute("user") User user) {
        if (borrowingReturning.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        // 创建时间
        borrowingReturning.setCreateTime(System.currentTimeMillis());
        // 借用时间
        borrowingReturning.setGetTime(System.currentTimeMillis());

        return new ResponseEntity(borrowingReturningService.save(borrowingReturning), HttpStatus.CREATED);
    }

    /**
     * 查询
     *
     * @param pageable
     * @return
     */
    @GetMapping(value = "/detailBorrowingReturning")
    public ResponseEntity detailBorrowingReturning(Pageable pageable, @RequestParam(required = false) String id) {
        Long jurisdiction = null;
        // 数据权限
        List<Long> list = new ArrayList(dataScope.getDeptIds());
        if (list != null && list.size() > 0) {
            jurisdiction = list.get(0);
        }
        return new ResponseEntity(borrowingReturningService.detailBorrowingReturning(pageable,id, jurisdiction), HttpStatus.OK);
    }


//    /**
//     * 修改
//     *
//     * @param borrowingReturning
//     * @return
//     */
//    @PutMapping(value = "/borrowingReturning/edit")
//    @PreAuthorize("hasAnyRole('ADMIN','BORROWING_RETURNING_ALL','BORROWING_RETURNING_EDIT')")
//    public ResponseEntity update(@RequestBody BorrowingReturning borrowingReturning) {
//        // 设置归还时间
//        borrowingReturning.setRepayTime(System.currentTimeMillis());
//        borrowingReturningService.updateById(borrowingReturning);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    /**
//     * 删除
//     *
//     * @param id
//     * @return
//     */
//    @DeleteMapping(value = "/borrowingReturning/{id}")
//    @PreAuthorize("hasAnyRole('ADMIN','BORROWING_RETURNING_ALL','BORROWING_RETURNING_DELETE')")
//    public ResponseEntity delete(@PathVariable Long id) {
//        borrowingReturningService.removeById(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
