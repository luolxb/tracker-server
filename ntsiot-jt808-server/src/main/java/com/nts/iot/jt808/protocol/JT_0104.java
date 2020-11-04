package com.nts.iot.jt808.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * 查询终端参数应答
 */
@Slf4j
@Data
public class JT_0104 implements IMessageBody {

    private String deviceNo;
    private String apn;
    private String ipAddress;
    private int port;
    private int work_fre;
    private int savemode_fre;

    /**
     * 应答流水号,对应的终端参数查询消息的流水号
     */
    @JSONField(serialize=false)
    private int responseMessageSerialNo;

    public final int getResponseMessageSerialNo() {
        return responseMessageSerialNo;
    }

    public final void setResponseMessageSerialNo(int value) {
        responseMessageSerialNo = value;
    }



    /**
     * 应答参数个数
     */
    @JSONField(serialize=false)
    private byte parametersCount;

    public final byte getParametersCount() {
        return parametersCount;
    }

    public final void setParametersCount(byte value) {
        parametersCount = value;
    }

    /**
     * 参数项列表
     */
    @JSONField(serialize=false)
    private ArrayList<ParameterItem> parameters;

    public final ArrayList<ParameterItem> getParameters() {
        return parameters;
    }

    public final void setParameters(ArrayList<ParameterItem> value) {
        parameters = value;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put((short) getResponseMessageSerialNo());
        buff.put(getParametersCount());
        for (ParameterItem item : getParameters()) {
            buff.put(item.getParameterId());
            switch (item.getParameterLength()) {
                case 1:
                    // 参数值类型为byte
                    buff.put(item.getParameterLength());
                    buff.put(Byte.parseByte("" + item.getParameterValue()));
                    break;
                case 2:
                    // 参数值类型为16位无符号整形数值
                    buff.put(item.getParameterLength());
                    buff.put(Short.parseShort("" + item.getParameterValue()));
                    break;
                case 4:
                    // 参数值类型为32位无符号整形数值
                    buff.put(item.getParameterLength());
                    buff.put(Integer.parseInt("" + item.getParameterValue()));
                    break;
                default:
                    // 参数值类型为字符串
                    byte[] strBytes = item.getParameterValue().toString().getBytes();
                    item.setParameterLength((byte) (strBytes.length));
                    buff.put(item.getParameterLength());
                    buff.put(strBytes);
                    break;
            }
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        //参数个数
        int parameterCount = buff.get();
        log.info("参数个数: "+parameterCount);
        while (buff.hasRemain())
        {
            int parameterIndex = buff.getInt();
            int paramLenth = buff.get();
            switch (parameterIndex)
            {
                case 0x0010:{
                    //APN
                    byte [] apns = buff.gets(paramLenth);
                    this.setApn(BitConverter.getString(apns));
                }break;
                case 0x0013:{
                    byte [] ipaddress = buff.gets(paramLenth);
//                    String ipaddressString =""+(ipaddress[0]&0xff)+"."+(ipaddress[1]&0xff)+"."+(ipaddress[2]&0xff)+"."+(ipaddress[3]&0xff);
                    this.setIpAddress(new String(ipaddress));
                }break;
                case 0x0018:{
                    int port = buff.getInt();
                    this.setPort(port);
                }break;
                case 0x0029:{
                    int normalFre = buff.getInt();
                    this.setWork_fre(normalFre);
                }break;
                case 0x0027:{
                    int sleepFre = buff.getInt();
                    this.setSavemode_fre(sleepFre);
                }break;
                default :break;
            }
        }
        log.info("收取的终端参数为: "+this.toString());

    }











//        ByteBuffer buff = new ByteBuffer(bytes);
//        this.responseMessageSerialNo = buff.getUnsignedShort();
//        setParametersCount(buff.get());
//        setParameters(new ArrayList<>());
//        int pos = 3;
//        while (buff.hasRemain()) {
//            ParameterItem item = new ParameterItem();
//            item.setParameterId(buff.getInt());
//            pos += 4;
//            item.setParameterLength(buff.get());
//            pos += 1;
//            switch (item.getParameterLength()) {
//                case 1:
//                    // 参数值为byte类型
//                    item.setParameterValue(buff.get());
//                    break;
//                case 2:
//                    // 参数值为ushort类型
//                    item.setParameterValue(buff.getShort());
//                    break;
//                case 4:
//                    // 参数值为uint类型
//                    int pId = item.getParameterId();
//                    if ((pId >= 0x0010 && pId <= 0x0017) || (pId >= 0x0040 && pId <= 0x0044) || pId == 0x0048 || pId == 0x0049 || pId == 0x0083) {
//                        byte[] strBytes = buff.gets(item.getParameterLength());
//                        String strValue;
//                        try {
//                            strValue = new String(strBytes, "gbk");
//                            item.setParameterValue(strValue);
//                        } catch (UnsupportedEncodingException e) {
//                        }
//                    } else {
//                        item.setParameterValue(buff.getInt());
//                    }
//                    break;
//                default:
//                    byte[] strBytes = buff.gets(item.getParameterLength());
//                    String strValue;
//                    try {
//                        strValue = new String(strBytes, "gbk");
//                        item.setParameterValue(strValue);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//            this.parameters.add(item);
//            pos += item.getParameterLength();
//        }
//    }

}
