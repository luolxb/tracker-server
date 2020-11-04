package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 位置信息查询应答
 */
public class JT_0201 implements IMessageBody {

	/**
	 * 对应的位置信息查询消息的流水号
	 */
	private short responseMessageSerialNo;

	/**
	 * 位置信息汇报
	 */
	private JT_0200 positionReport;

	@Override
	public final byte[] writeToBytes() {
		ByteBuffer buff = new ByteBuffer();
		buff.put(getResponseMessageSerialNo());
		buff.put(getPositionReport().writeToBytes());
		return buff.toByteArrayAndRelease();
	}

	@Override
	public final void readFromBytes(byte[] bytes) {
		ByteBuffer buff = new ByteBuffer(bytes);
		setResponseMessageSerialNo(buff.getShort());
		setPositionReport(new JT_0200());
		getPositionReport().readFromBytes(buff.gets(bytes.length - 2));
	}

	@Override
	public String toString() {
		return String.format("流水号:%1$s,%2$s", getResponseMessageSerialNo(), getPositionReport().toString());
	}

	public final short getResponseMessageSerialNo() {
		return responseMessageSerialNo;
	}

	public final void setResponseMessageSerialNo(short value) {
		responseMessageSerialNo = value;
	}

	public final JT_0200 getPositionReport() {
		return positionReport;
	}

	public final void setPositionReport(JT_0200 value) {
		positionReport = value;
	}
}