package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.AboutUs;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhc@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface AboutUsService extends IService<AboutUs> {

    /**
     * 列表查询
     * @param title
     * @param jurisdictions
     * @param pageable
     * @return
     */
    Map queryAll(String title, List<String> jurisdictions, Pageable pageable);

    /**
     * 新增
     * @param aboutUs
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer saveAboutUs(AboutUs aboutUs);


    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer deleteById(Long id);


    /**
     * 编辑
     * @param aboutUs
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer updateAboutUs(AboutUs aboutUs);

    /**
     * 根据辖区获取信息
     * @param id
     * @return
     */
    AboutUs getAboutUsById(Long id);

}
