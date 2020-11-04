package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.CommonProblem;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/6 11:20
 * @Description:
 */
public interface CommonProblemService extends IService<CommonProblem> {

    /**
     * 列表查询
     * @param title
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

    /**
     * 新增常见问题
     * @param commonProblem
     */
    @Transactional(rollbackFor = Exception.class)
    void saveProblem(CommonProblem commonProblem);

    /**
     * 根据辖区获取常见问题
     * @param deptId
     * @return
     */
    List<CommonProblem> getProblemsByDeptId(Long deptId);

    /**
     * 修改
     * @param commonProblem
     */
    @Transactional(rollbackFor = Exception.class)
    void updateProblemById(CommonProblem commonProblem);
}
