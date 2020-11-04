package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.protocol.constant.JT808Constant;
import io.netty.util.internal.StringUtil;

/**
 * 设置终端参数
 */
public class JT_8103 implements IMessageBody {
    /**
     * 参数个数
     */
    private byte parametersCount;

    public final byte getParametersCount() {
        return parametersCount;
    }

    public final void setParametersCount(byte value) {
        parametersCount = value;
    }

    /**
     * 参数列表
     */
    private java.util.ArrayList<ParameterItem> parameters;

    public final java.util.ArrayList<ParameterItem> getParameters() {
        return parameters;
    }

    public final void setParameters(java.util.ArrayList<ParameterItem> value) {
        parameters = value;
    }

    private String getFieldType(int paramField) {
        return "" + JT808Constant.getParamType(paramField);
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getParametersCount());
        for (ParameterItem item : getParameters()) {
            buff.put(item.getParameterId());
            String fieldType = getFieldType(item.getParameterId());
            String strParamValue = "" + item.getParameterValue();
            // 参数值类型为byte
            if (fieldType.equals("BYTE") && StringUtil.isNullOrEmpty(strParamValue) == false) {
                item.setParameterLength((byte) 1);
                buff.put(item.getParameterLength());
                buff.put(Byte.parseByte(strParamValue));
                // 参数值类型为16位无符号整形数值
            } else if (fieldType.equals("WORD") && StringUtil.isNullOrEmpty(strParamValue) == false) {
                item.setParameterLength((byte) 2);
                buff.put(item.getParameterLength());
                buff.put(Short.parseShort(strParamValue));
                // 参数值类型为32位无符号整形数值
            } else if (fieldType.equals("DWORD") && StringUtil.isNullOrEmpty(strParamValue) == false) {
                item.setParameterLength((byte) 4);
                buff.put(item.getParameterLength());
                int paramValue1 = Integer.parseInt(strParamValue);
                buff.put(paramValue1);
                // 参数值类型为字符串
            } else {
                byte[] strBytes = BitConverter.getBytes(item.getParameterValue().toString());
                item.setParameterLength((byte) (strBytes.length));
                buff.put(item.getParameterLength());
                buff.put(strBytes);
            }
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);
        setParametersCount(buff.get());
        setParameters(new java.util.ArrayList<>(getParametersCount()));
        int pos = 1;
        while (buff.hasRemain()) {
            ParameterItem item = new ParameterItem();
            item.setParameterId(buff.getInt());
            pos += 4;
            item.setParameterLength(buff.get());
            pos += 1;
            String fieldType = getFieldType((int) item.getParameterId());
            // 参数值为byte类型
            if (fieldType.equals("BYTE")) {
                item.setParameterValue(buff.get());
                // 参数值为ushort类型
            } else if (fieldType.equals("WORD")) {
                item.setParameterValue(buff.getShort());
                // 参数值为uint类型
            } else if (fieldType.equals("DWORD")) {
                item.setParameterValue(buff.getInt());
            } else {
                byte[] strBytes = buff.gets(item.getParameterLength());
                String strValue = BitConverter.getString(strBytes, 0, strBytes.length);
                item.setParameterValue(strValue);
            }
            getParameters().add(item);
            pos += item.getParameterLength();
        }
    }
}