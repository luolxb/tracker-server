package com.nts.iot.modules.system.rest;

import com.nts.iot.modules.system.model.Dept;
import com.nts.iot.modules.system.model.Question;
import com.nts.iot.modules.system.model.User;
import com.nts.iot.modules.system.service.DeptService;
import com.nts.iot.modules.system.service.EvaluationService;
import com.nts.iot.modules.system.service.QuestionService;
import com.nts.iot.modules.system.service.QuestionnaireService;
import com.nts.iot.aop.log.Log;
import com.nts.iot.base.controller.JwtBaseController;
import com.nts.iot.modules.system.dto.QuestionnaireDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 题库
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/3 09:56
 * @Description:
 */
@RestController
@RequestMapping("api")
public class QuestionController extends JwtBaseController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private EvaluationService evaluationService;

    /**
     * 问题列表查询
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("问题列表查询")
    @GetMapping(value = "/questions")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTION_ALL','QUESTION_DELETE')")
    public ResponseEntity getList(String title, Pageable pageable, @ModelAttribute("user") User user){
        return new ResponseEntity(questionService.queryAll(title, this.getJurisdictions(user), pageable), HttpStatus.OK);
    }

    /**
     * 新增问题
     * @param question
     * @param user
     * @return
     */
    @Log("新增问题")
    @PostMapping("/question/add")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTION_ALL','QUESTION_CREATE')")
    public ResponseEntity saveQuestion(@RequestBody Question question, @ModelAttribute("user") User user){
        questionService.saveQuestion(question);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 获取调查题目
     * @return
     */
    @Log("查看调查题目")
    @GetMapping("/question/get")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTION_ALL','QUESTION_DELETE')")
    public ResponseEntity getQuestion(@ModelAttribute("user") User user) {
        return new ResponseEntity(questionService.getQuestion(null), HttpStatus.CREATED);
    }

    /**
     * 删除问题
     * @param id
     * @return
     */
    @Log("删除问题")
    @DeleteMapping(value = "/question/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTION_ALL','QUESTION_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        questionService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 更改问题状态，是否显示
     * @param id
     * @return
     */
    @Log("更改问题状态")
    @PostMapping(value = "/question/changeStatus")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTION_ALL','QUESTION_EDIT')")
    public ResponseEntity updataStatus(Long id, String status) {
        questionService.updataStatus(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 问卷调查列表
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("问卷调查列表")
    @GetMapping(value = "/questionnaires")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTIONNAIRES_ALL','QUESTIONNAIRES_DELETE')")
    public ResponseEntity getQuestionnaires(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = this.getJurisdictions(user);
        return new ResponseEntity(questionnaireService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }
    /**

     * 创建调查问卷
     * @param questionnaireDTO
     * @param user
     * @return
     */
    @Log("创建调查问卷")
    @PostMapping("/questionnaire/add")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTIONNAIRES_ALL','QUESTIONNAIRES_CREATE')")
    public ResponseEntity saveQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO, @ModelAttribute("user") User user){
        questionnaireDTO.setJurisdiction(null);
        questionnaireService.saveQuestionnaire(questionnaireDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 删除问卷
     * @param id
     * @return
     */
    @Log("删除问卷")
    @DeleteMapping(value = "/questionnaire/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTIONNAIRES_ALL','QUESTIONNAIRES_DELETE')")
    public ResponseEntity deleteQuestionnaire(@PathVariable Long id) {
        questionnaireService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 改变问卷显示状态
     * @param id
     * @param status
     * @return
     */
    @Log("改变问卷显示状态")
    @PostMapping(value = "/questionnaire/changeStatus")
    @PreAuthorize("hasAnyRole('ADMIN','QUESTIONNAIRES_ALL','QUESTIONNAIRES_EDIT')")
    public ResponseEntity updataQuestionnaireStatus(Long id, String status) {
        questionnaireService.updataStatus(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * 获取辖区
     * @param user
     * @return
     */
    private List<String> getJurisdictions(User user) {
        List<String> jurisdictions = new ArrayList<>();
        List<Dept> deptList = deptService.findByUserRole(user);
        for (int i = 0; i < deptList.size(); i++) {
            Dept dept = deptList.get(i);
            jurisdictions.add(String.valueOf(dept.getId()));
        }
        return jurisdictions;
    }

    /**
     * 获取评测列表
     * @param title
     * @param pageable
     * @param user
     * @return
     */
    @Log("评测列表查询")
    @GetMapping(value = "/answers")
    @PreAuthorize("hasAnyRole('ADMIN','EVALUATIONALL')")
    public ResponseEntity getAnswers(String title, Pageable pageable, @ModelAttribute("user") User user){
        List<String> jurisdictions = this.getJurisdictions(user);
        return new ResponseEntity(evaluationService.queryAll(title, jurisdictions, pageable), HttpStatus.OK);
    }

}
