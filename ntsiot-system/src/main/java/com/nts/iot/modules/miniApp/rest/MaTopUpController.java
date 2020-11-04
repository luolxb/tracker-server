package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.system.service.TopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ma")
public class MaTopUpController {

    @Autowired
    private TopUpService topUpService;

    /**
     * 根据辖区id查询金额
     *
     * @param jurisdiction
     * @return
     */
    @GetMapping(value = "/topUp/getMoney")
    public List<String> getMoney(String jurisdiction) {
        return topUpService.queryMoneyByJurisdiction(Long.parseLong(jurisdiction));
    }
}
