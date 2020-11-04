package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 查询终端参数,查询终端参数消息体为空。则返回主要的参数。也可下发查询具体的几个参数；
 */
public class JT_8104 implements IMessageBody {

	/**
	 * 要查询的参数个数
	 */
	private byte parametersCount;

	public final byte getParametersCount() {
		return parametersCount;
	}

	public final void setParametersCount(byte value) {
		parametersCount = value;
	}

	/**
	 * 要查询的参数列表
	 */
	private java.util.ArrayList<Integer> parametersIDs;

	public final java.util.ArrayList<Integer> getParametersIDs() {
		return parametersIDs;
	}

	public final void setParametersIDs(java.util.ArrayList<Integer> value) {
		parametersIDs = value;
	}

	@Override
	public final byte[] writeToBytes() {
		if (getParametersCount() > 0 && getParametersIDs() != null && getParametersIDs().size() > 0) {
			ByteBuffer buff = new ByteBuffer();
			buff.put(getParametersCount());
			if (getParametersCount() > 0 && getParametersIDs() != null && getParametersIDs().size() > 0) {
				for (int parameterId : getParametersIDs()) {
					buff.put(parameterId);
				}
			}
			return buff.toByteArrayAndRelease();
		}
		return null;
	}

	@Override
	public final void readFromBytes(byte[] bytes) {
		if (bytes != null && bytes.length > 0) {
			ByteBuffer buff = new ByteBuffer(bytes);
			setParametersCount(buff.get());
			setParametersIDs(new java.util.ArrayList<>(getParametersCount()));
			int pos = 1;
			while (buff.hasRemain()) {
				getParametersIDs().add(buff.getInt());
				pos += 4;
			}
		}
	}
}