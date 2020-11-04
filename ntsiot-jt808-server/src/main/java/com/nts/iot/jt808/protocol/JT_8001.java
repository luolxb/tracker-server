package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 平台通用应答
 */
public class JT_8001 implements IMessageBody {

	/**
	 * 应答消息流水号
	 */
	private short responseMessageSerialNo;

	public final short getResponseMessageSerialNo() {
		return responseMessageSerialNo;
	}

	public final void setResponseMessageSerialNo(short value) {
		responseMessageSerialNo = value;
	}

	/**
	 * 应答消息ID
	 */
	private short responseMessageId;

	public final short getResponseMessageId() {
		return responseMessageId;
	}

	public final void setResponseMessageId(short value) {
		responseMessageId = value;
	}

	/**
	 * 应答结果，0：成功/确认；1：失败；2：消息有误；3：不支持；4：报警处理确认；
	 */
	private byte responseResult;

	public final byte getResponseResult() {
		return responseResult;
	}

	public final void setResponseResult(byte value) {
		responseResult = value;
	}

	@Override
	public String toString() {
		return "应答结果:"+responseResult;
	}

	@Override
	public final byte[] writeToBytes() {
		ByteBuffer buff = new ByteBuffer();
		buff.put(getResponseMessageSerialNo());
		buff.put(getResponseMessageId());
		buff.put(getResponseResult());
		return buff.toByteArrayAndRelease();
	}

	@Override
	public final void readFromBytes(byte[] messageBodyBytes) {
		setResponseMessageSerialNo((short) BitConverter.toUInt16(messageBodyBytes, 0));
		setResponseMessageId((short) BitConverter.toUInt16(messageBodyBytes, 2));
		setResponseResult(messageBodyBytes[4]);
	}
}