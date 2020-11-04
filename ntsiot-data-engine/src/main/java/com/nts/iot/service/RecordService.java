/*******************************************************************************
 * @(#)RecordService.java 2019-05-14
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.service;

import com.nts.iot.entity.Record;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version cyoubike 1.0 $ 2019-05-14 20:29
 */
public interface RecordService {

    void save(Record record);

    void saveAll(List<Record> records);

}
