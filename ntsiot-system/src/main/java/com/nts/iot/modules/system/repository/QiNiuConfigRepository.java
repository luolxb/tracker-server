package com.nts.iot.modules.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nts.iot.modules.system.domain.QiniuConfig;

/**
 * @author jie
 * @date 2018-12-31
 */
public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig,Long> {
}
