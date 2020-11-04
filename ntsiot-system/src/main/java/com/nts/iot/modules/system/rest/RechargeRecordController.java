package com.nts.iot.modules.system.rest;


import com.nts.iot.modules.miniApp.service.RechargeRecordService;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.config.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户充值历史
 */
@RestController
@RequestMapping("api")
public class RechargeRecordController extends JwtBaseController {

    @Autowired
    private DataScope dataScope;

    @Autowired
    private RechargeRecordService rechargeRecordService;


    private static final String ENTITY_NAME = "rechargeRecord";

    /**
     * 查询所有用户账户分页
     *
     * @param userName
     * @param jurisdiction
     * @param pageable
     * @return
     */
    @GetMapping(value = "/rechargeRecord")
    @PreAuthorize("hasAnyRole('ADMIN','TOP_UP_ALL','TOP_UP_SELECT')")
    public ResponseEntity getRechargeRecords(Pageable pageable, @RequestParam(required = false) String userName, @RequestParam(required = false) Long jurisdiction) {
//        Long jurisdiction = null;
//        // 数据权限
//        Set<Long> deptIds = dataScope.getDeptIds();
//        List<Long> list = new ArrayList(deptIds);
//        if (list != null && list.size() > 0) {
//            jurisdiction = list.get(0);
//        }
        return new ResponseEntity(rechargeRecordService.queryAll(pageable, userName, jurisdiction), HttpStatus.OK);
    }
}
