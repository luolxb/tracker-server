/*******************************************************************************
 * @(#)ClassUtils.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.utils;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 18:28
 */
public class ClassUtils {

    public static Object getBean(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        }
        catch (Exception ex) {
        }
        if (clazz != null) {
            try {
                return clazz.newInstance();
            }
            catch (Exception ex) {
            }
        }
        return null;
    }

}
