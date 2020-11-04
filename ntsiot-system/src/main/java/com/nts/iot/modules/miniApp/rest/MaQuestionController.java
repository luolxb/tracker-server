package com.nts.iot.modules.miniApp.rest;

import com.nts.iot.modules.system.dto.EvaluationDTO;
import com.nts.iot.modules.system.service.EvaluationService;
import com.nts.iot.modules.system.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 题库
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:56
 * @Description:
 */
@RestController
@RequestMapping("ma")
public class MaQuestionController{

//    @Autowired
//    private QuestionService questionService;

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private QuestionnaireService questionnaireService;

//    /**
//     * 获取题库
//     * @return
//     */
//    @GetMapping("/questionnaire/get")
//    public ResponseEntity getQuestionnaire() {
//        return new ResponseEntity(questionService.getQuestionnaire(), HttpStatus.CREATED);
//    }

    /**
     * 保存问卷答案
     * @return
     */
    @PostMapping("/answer/add")
    public ResponseEntity saveAnswer(@RequestBody List<EvaluationDTO> answers) {
        evaluationService.saveAnswer(answers);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 创建调查问卷
     * @param deptId
     * @return
     */
    @GetMapping("/questionnaire/get")
    public ResponseEntity createQuestionnaire(Long deptId){
        return new ResponseEntity(questionnaireService.createQuestionnaire(deptId), HttpStatus.CREATED);
    }

}
