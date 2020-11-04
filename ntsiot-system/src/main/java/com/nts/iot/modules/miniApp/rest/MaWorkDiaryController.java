/*******************************************************************************
 * @(#)WechatComplaint.java 2018-11-01
 *
 * Copyright 2018 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.miniApp.model.WorkDiary;
import com.nts.iot.modules.miniApp.service.WorkDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <p>
 * 工作日志上报
 * </p>
 *
 * @author <a href="mailto:cej@rnstec.com">迟恩军</a>
 * @version fst 1.0 $ 2018-11-01 09:58
 */
@RestController
@RequestMapping("ma")
public class MaWorkDiaryController {
    @Autowired
    private WorkDiaryService workDiaryService;

    /**
     * 添加工作日报
     *
     * @param workDiary
     * @return
     */
    @PostMapping(value = "/workDiary/save")
    public String save(@RequestBody WorkDiary workDiary) {
        String result;
        try {
            workDiaryService.saveWorkDiary(workDiary);
            result = "SUCCESS";
        } catch (Exception e) {
            result = "ERROR";
            e.printStackTrace();
        }
        return result;
    }
}