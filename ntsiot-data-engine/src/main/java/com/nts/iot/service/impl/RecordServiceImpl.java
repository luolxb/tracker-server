/*******************************************************************************
 * @(#)RecordServiceImpl.java 2019-05-14
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.service.impl;

import com.nts.iot.entity.Record;
import com.nts.iot.repository.RecordRepository;
import com.nts.iot.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-14 20:29
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void save(Record record) {
        recordRepository.save(record);
    }

    @Override
    public void saveAll(List<Record> records) {
        recordRepository.saveAll(records);
    }
}
