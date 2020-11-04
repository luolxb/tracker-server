/*******************************************************************************
 * @(#)SecurityRepository.java 2019-05-18
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.repository;

import com.nts.iot.entity.Security;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-18 15:45
 */
public interface SecurityRepository extends ElasticsearchRepository<Security, String> {

    /**
     * 通过辖区查询Security
     *
     * @param id 年-月-日-辖区id
     * @return
     */
    Security findAllById(String id);


}
