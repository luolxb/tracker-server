/*******************************************************************************
 * @(#)T808Message.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.message;

import com.alibaba.fastjson.JSON;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.utils.Tools;
import com.nts.iot.jt808.protocol.message.factory.T808MessageFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 17:35
 */
@Slf4j
public class T808Message {

    public static final int DONE = 1;
    public static final int ERROR = 0;
    public static final int NOT_DONE = 2;
    public static final int MORE = 3;

    // 0x0001 终端通用应答
    public static final Integer COMMON_RESPONSE = 0x0001;
    // 0x0100 注册信息包
    public static final Integer REQUEST_REGISTER = 0x0100;
    // 0x0102 终端鉴权
    public static final Integer REQUEST_AUTHENTIFICATION = 0x0102;
    // 0x0002 心跳
    public static final Integer REQUEST_HEARTBEAT = 0x0002;
    // 0x0200 位置信息汇报
    public static final Integer REQUEST_LOCATION = 0x0200;
    // 0x0202 批量位置信息汇报
    public static final Integer REQUEST_MULTI_LOCATIONS=0x0202;
    // 0x9100 反控请求
    public static final Integer REQUEST_COUNTERCONTROL = 0x9100;

    // 0x8104 查询参数请求
    public static final Integer REQUEST_SEARCH_PARAM = 0x8104;
    // 0x0104 查询参数应答
    public static final Integer RESPONSE_SEARCH_PARAM = 0x0104;

    // 0x8103 修改参数请求
    public static final Integer REQUEST_EDIT_PARAM  = 0x8103;
    // 0x0103 修改参数应答
    public static final Integer RESPONSE_EDIT_PARAM = 0x0103;
    // 0x0106 WiFi/LBS 定位信息上报
    public static final Integer REQUEST_LOCATION_WiFi = 0x0106;
    // 0x0109 时间同步
    public static final Integer REQUEST_TIME_SYN = 0x0109;


    // 0x8107 查询属性请求
    public static final Integer REQUEST_SEARCH_ATTRIBUTE = 0x8107;
    // 0x0107 查询属性应答
    public static final Integer RESPONSE_SEARCH_ATTRIBUTE = 0x0107;

    // 0x8500 控制终端请求
    public static final Integer REQUEST_APP_CONTROL = 0x8500;
    // 0x0500 控制终端应答
    public static final Integer RESPONSE_APP_CONTROL = 0x0500;
    // 0x0108 升级结果
    public static final Integer RESPONSE_UPGRADE_RESULT = 0x0108;
    /* 控制应答状态 */

    // 远程开锁应答
    public static final String RESPONSE_STATUS_OPEN = "0100";

    // 远程关锁应答
    public static final String RESPONSE_STATUS_CLOSE = "0101";

    // 远程临时开锁应答
    public static final String RESPONSE_STATUS_TEMP_OPEN = "1100";

    // 远程临时关锁应答
    public static final String RESPONSE_STATUS_TEMP_CLOSE = "1101";


    //车门控制类型
    public static final Integer DEVICE_CONTROL_TYPE_LOCK = 0;
    //上锁
    public static final Integer DEVICE_CONTROL_LOCKED = 1;
    //开锁
    public static final Integer DEVICE_CONTROL_UNLOCKED = 0;

    //蜂鸣器控制类型
    public static final Integer DEVICE_CONTROL_TYPE_BUZZER = 0;
    //打开蜂鸣器
    public static final Integer DEVICE_CONTROL_OPEN_BUZZER = 1;
    //关闭蜂鸣器
    public static final Integer DEVICE_CONTROL_CLOSE_BUZZER = 0;


    private T808MessageHeader header = new T808MessageHeader();

    private int status;

    /**
     * 如果是分包，分包是不能解析的，等待和其他包合成一个完整的包才能解析
     */
    private byte[] childPacket;

    private static byte _PrefixID = 0x7E;

    private byte[] _checkSum = null;

    private IMessageBody messageContents;

    private String plateNo;

    private String packetDescr;

    private String errorMessage;



    public T808Message() {

    }

    public T808Message(String simNo, int messageType, IMessageBody echoData) {
        this.setMessageContents(echoData);
        this.setHeader(new T808MessageHeader());
        this.getHeader().setMessageType(messageType);
        this.getHeader().setSimId(simNo);
        this.getHeader().setIsPackage(false);
    }



    public String getSimNo() {
        if (header != null) {
            return header.getSimId();
        }
        return "";
    }

    @Override
    public String toString() {
        if (messageContents != null) {
            return messageContents.toString();
        } else if (this.getHeader().getIsPackage()) {
            String str =  "分包号:" + this.getHeader().getMessagePacketNo() + ",总包数:" + this.getHeader().getMessageTotalPacketsCount();
            if(childPacket != null && childPacket.length >0) {
                str += ", 分包长度:" + childPacket.length;
            }
            return str;
        }
        System.out.println(header.getSimId());
        return "";
    }

    public final int getStatus() {
        return status;
    }

    public final void setStatus(int value) {
        status = value;
    }

    /**
     * 报文头
     */
    public final T808MessageHeader getHeader() {
        return header;
    }

    public final void setHeader(T808MessageHeader value) {
        header = value;
    }



    public static byte getPrefixID() {
        return _PrefixID;
    }

    public final int getMessageType() {
        return getHeader().getMessageType();
    }



    public final byte[] getCheckSum() {
        return _checkSum;
    }



    public final IMessageBody getMessageContents() {
        return messageContents;
    }

    public final void setMessageContents(IMessageBody value) {
        messageContents = value;
    }



    public final String getPlateNo() {
        return plateNo;
    }

    public final void setPlateNo(String value) {
        plateNo = value;
    }



    public final String getPacketDescr() {
        return packetDescr;
    }

    public final void setPacketDescr(String value) {
        packetDescr = value;
    }



    public final String getErrorMessage() {
        return errorMessage;
    }

    public final void setErrorMessage(String value) {
        errorMessage = value;
    }

    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();

        byte[] bodyBytes = null;
        if (getMessageContents() != null) {
            bodyBytes = getMessageContents().writeToBytes();
        }
        if (bodyBytes != null) {
            header.setMessageSize(bodyBytes.length);
            header.setIsPackage(false);
            byte[] headerBytes = header.writeToBytes();
            buff.put(headerBytes);
            buff.put(bodyBytes);
        } else {
            header.setMessageSize(0);
            byte[] headerBytes = header.writeToBytes();
            buff.put(headerBytes);
        }
        byte[] messageBytes = buff.array();
        byte checkSum = getCheckXor(messageBytes, 0, messageBytes.length);
        buff.put(checkSum);
        // 转义
        byte[] escapedBytes = escape(buff.array());
        buff.clear();
        buff.put(_PrefixID);
        buff.put(escapedBytes);
        buff.put(_PrefixID);

        byte[] data = buff.array();
        this.packetDescr = Tools.ToHexString(data);

        return buff.toByteArrayAndRelease();

    }

//7e01020006c37043773962007d02616231343030a07e
    public final void readFromBytes(byte[] messageBytes) {
        //System.out.println("********* 原始报文: " + Tools.ToHexString(messageBytes));

        this.packetDescr = Tools.ToHexString(messageBytes);
        byte[] validMessageBytes = unEscape(messageBytes);
        try {
            // 检测校验码
            byte xor = getCheckXor(validMessageBytes, 1, validMessageBytes.length - 2);
            byte realXor = validMessageBytes[validMessageBytes.length - 2];
            if (xor == realXor) {
                _checkSum = new byte[] { xor };
                try {
                    try {
                        int tempLen = validMessageBytes.length - 1 - 1 - 1;
                        byte[] headerBytes = new byte[tempLen < 16 ? 12 : 16];
                        System.arraycopy(validMessageBytes, 1, headerBytes, 0,
                                headerBytes.length);
                        header.readFromBytes(headerBytes);

                        int start = 17;
                        // 不分包则少4个字节的分包信息
                        if (!header.getIsPackage()) {
                            start = 13;
                        }
                        if (header.getMessageSize() > 0) {
                            byte[] sourceData = new byte[header.getMessageSize()];
                            System.arraycopy(validMessageBytes, start, sourceData, 0, sourceData.length);
                            if (header.getIsPackage()) {
                                // 分包的消息体是纯数据不进行解析，保留在消息中.
                                childPacket = new byte[header.getMessageSize()];
                                System.arraycopy(sourceData, 0, childPacket, 0, header.getMessageSize());
                            } else {
                                // 其余的包都进行解析
                                setMessageContents(T808MessageFactory.create(header.getMessageType(), sourceData));
                            }
                        }
                    } finally {
//                         binaryReader.dispose();
                    }
                } finally {
                    // ms.dispose();
                }
            } else {
                setErrorMessage("校验码不正确");
                log.error(getErrorMessage() + ":" + Tools.ToHexFormatString(messageBytes));
            }
        } catch (Exception ex) {
            log.error("T808Message : ReadFromBytes() " + Tools.ToHexFormatString(messageBytes));
            setErrorMessage("解析异常:" + ex.getMessage());
            log.error(getErrorMessage(), ex);
        }
    }

    /**
     * 获取校验和
     *
     * @param data
     * @param pos
     * @param len
     * @return
     */
    private byte getCheckXor(byte[] data, int pos, int len) {
        byte A = 0;
        for (int i = pos; i < len; i++) {
            A ^= data[i];
        }
        return A;
    }

    /**
     * 将标识字符的转义字符还原
     *
     * @param data
     * @return
     */
    private byte[] unEscape(byte[] data) {
        ByteBuffer buff = new ByteBuffer();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0x7D) {
                if (data[i + 1] == 0x01) {
                    buff.put((byte) 0x7D);
                    i++;
                } else if (data[i + 1] == 0x02) {
                    buff.put((byte) 0x7E);
                    i++;
                }
            } else {
                buff.put(data[i]);
            }
        }
        return buff.toByteArrayAndRelease();
    }

    /**
     * 加入标示符的转义进行封装
     *
     * @param data
     * @return
     */
    private byte[] escape(byte[] data) {
        ByteBuffer tmp = new ByteBuffer();
        for (int j = 0; j < data.length; j++) {
            if (data[j] == 0x7D) {
                tmp.put((byte) 0x7D);
                tmp.put((byte) 0x01);
            } else if (data[j] == 0x7E) {
                tmp.put((byte) 0x7D);
                tmp.put((byte) 0x02);
            } else {
                tmp.put(data[j]);
            }
        }

        return tmp.toByteArrayAndRelease();
    }

    public byte[] getChildPacket() {
        return childPacket;
    }

    public void setChildPacket(byte[] childPacket) {
        this.childPacket = childPacket;
    }


    /* jt0200 发送位置 */
    public static void main(String[] args) {
//        T808Message jt0200 = new T808Message();
//        byte[] bytes = Tools.HexString2Bytes("7e0200003ac280360653810000000000000000000001d967da073d2c20013f000a04f900000000000030011f310109e104073d2c20e2020040e306000000021068e504040186a53e7e");
//        jt0200.readFromBytes(bytes);
//
//        System.out.println(jt0200.getHeader().getSimId());
//        System.out.println(jt0200.toString());
//        System.out.println(JSON.toJSONString(jt0200));
//        CollectMessage message = new CollectMessage(jt0200);
//        System.out.println(JSON.toJSONString(message));

        T808Message jt0100 = new T808Message();
        String message = "7e0100002fc2803617644400000000000000000000000000000000000000000000000000000000000000d5647670f899000389860401101790011446767e";
        byte[] bytes1 = Tools.HexString2Bytes(message);
        jt0100.readFromBytes(bytes1);
        System.out.println(JSON.toJSONString(jt0100));


    }

//    /* JT_0100 注册  */
//    public static void main(String[] args) {
//        T808Message message = new T808Message();
//        byte[] bytes = Tools.HexString2Bytes("7e0100002f06307397883100e4002c012f373038363658432d4435320000000000000000000000000000d436398b52b20002898602b6101680648021a37e");
//        message.readFromBytes(bytes);
//        JT_0100 jt_0100 = (JT_0100) message.getMessageContents();
//        System.out.println(message.getHeader().getSimId());
//        System.out.println(jt_0100.toString());
//    }

//    /* JT_0102 鉴权  */
//    public static void main(String[] args) {
//        T808Message message = new T808Message();
//        byte[] bytes = Tools.HexString2Bytes("7e010200060630739788310008633134346437677e");
//        message.readFromBytes(bytes);
//        JT_0102 jt_0102 = (JT_0102) message.getMessageContents();
//        System.out.println(message.getHeader().getSimId());
//        System.out.println(jt_0102.toString());
//    }





}
