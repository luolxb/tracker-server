package com.nts.iot.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nts.iot.modules.system.domain.QiniuContent;

/**
 * @author jie
 * @date 2018-12-31
 */
public interface QiniuContentRepository extends JpaRepository<QiniuContent,Long>, JpaSpecificationExecutor {

    /**
     * 根据key查询
     * @param key
     * @return
     */
    QiniuContent findByKey(String key);
}
