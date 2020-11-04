/*******************************************************************************
 * @(#)T808MessageFactory.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.message.factory;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.utils.ClassUtils;
import com.nts.iot.jt808.utils.Tools;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:27
 */
public class T808MessageFactory {

    public static IMessageBody create(int messageType, byte[] messageBodyBytes) {
        String nameSpace = "com.nts.iot.jt808.protocol";
        String className = nameSpace + ".JT_" + Tools.ToHexString(messageType,2).toUpperCase();
        Object messageBody = ClassUtils.getBean(className);
        if (messageBody != null) {
            IMessageBody msg = (IMessageBody)messageBody;
            msg.readFromBytes(messageBodyBytes);
            return msg;
        }
        return null;
    }

}
