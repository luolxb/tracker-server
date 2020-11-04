package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.miniApp.service.AccountService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import com.nts.iot.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户钱包账户
 */
@RestController
@RequestMapping("api")
public class AccountController extends JwtBaseController {

    @Autowired
    private DataScope dataScope;

    @Autowired
    private AccountService accountService;


    private static final String ENTITY_NAME = "account";

    /**
     * 查询所有用户账户分页
     *
     * @param userName
     * @param jurisdiction
     * @param pageable
     * @return
     */
    @Log("用户钱包账户查询")
    @GetMapping(value = "/account")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getAccounts(Pageable pageable, @RequestParam(required = false) String userName, @RequestParam(required = false) Long jurisdiction) {
//        Long jurisdiction = null;
//        // 数据权限
//        Set<Long> deptIds = dataScope.getDeptIds();
//        List<Long> list = new ArrayList(deptIds);
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(accountService.queryAll(pageable, userName, jurisdiction), HttpStatus.OK);
    }

}
