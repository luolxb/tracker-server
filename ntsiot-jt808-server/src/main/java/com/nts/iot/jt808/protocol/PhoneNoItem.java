/*******************************************************************************
 * @(#)PhoneNoItem.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 23:03
 */
public class PhoneNoItem {

    /**
     * 标志,1：呼入；2：呼出；3：呼入/呼出
     */
    private byte dailFlag;

    public final byte getDailFlag() {
        return dailFlag;
    }

    public final void setDailFlag(byte value) {
        dailFlag = value;
    }

    /**
     * 号码长度
     */
    private byte phoneNoLength;

    public final byte getPhoneNoLength() {
        return phoneNoLength;
    }

    public final void setPhoneNoLength(byte value) {
        phoneNoLength = value;
    }

    /**
     * 电话号码
     */
    private String phoneNo;

    public final String getPhoneNo() {
        return phoneNo;
    }

    public final void setPhoneNo(String value) {
        phoneNo = value;
    }

    /**
     * 联系人长度
     */
    private byte contactLength;

    public final byte getContactLength() {
        return contactLength;
    }

    public final void setContactLength(byte value) {
        contactLength = value;
    }

    /**
     * 联系人
     */
    private String contact;

    public final String getContact() {
        return contact;
    }

    public final void setContact(String value) {
        contact = value;
    }

}
