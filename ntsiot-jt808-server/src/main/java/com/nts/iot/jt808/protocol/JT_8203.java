package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 报警解除
 */
public class JT_8203 implements IMessageBody {

	/**
	 * 流水号
	 */
	private short responseMessageSerialNo;

	public final short getResponseMessageSerialNo() {
		return responseMessageSerialNo;
	}

	public final void setResponseMessageSerialNo(short value) {
		responseMessageSerialNo = value;
	}

	/**
	 * 报警类型
	 */
	private int alarmType;

	@Override
	public String toString() {
		return "解除报警类型:"+getAlarmType();
	}

	@Override
	public final byte[] writeToBytes() {
		ByteBuffer buff = new ByteBuffer();
		buff.put(getResponseMessageSerialNo());
		buff.put(this.getAlarmType());
		return buff.toByteArrayAndRelease();
	}

	@Override
	public final void readFromBytes(byte[] messageBodyBytes) {
		setResponseMessageSerialNo((short) BitConverter.toUInt16(messageBodyBytes, 0));
		this.setAlarmType(BitConverter.toUInt32(messageBodyBytes, 2));
	}

	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}
}