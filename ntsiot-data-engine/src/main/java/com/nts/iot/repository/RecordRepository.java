/*******************************************************************************
 * @(#)Record.java 2019-05-13
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.repository;

import com.nts.iot.entity.Record;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-13 17:24
 */
public interface RecordRepository extends ElasticsearchCrudRepository<Record, String> {
    Optional<Record> findById(String id);
    List<Record> findAllById(String id);


}
