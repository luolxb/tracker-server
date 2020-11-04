package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

/**
 * 多媒体数据上传应答
 */
public class JT_8800 implements IMessageBody {

	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("多媒体ID:" + multimediaDataId).append(",重传包数:" + repassPacketsCount);
		if (this.repassPacketsNo != null && this.repassPacketsNo.size() > 0) {
			sBuilder.append(",重传包Id列表:");
			for (short packetNo : this.repassPacketsNo) {
				sBuilder.append(packetNo).append(",");
			}
		}
		return sBuilder.toString();
	}

	/**
	 * 多媒体ID
	 */
	private int multimediaDataId;

	public final int getMultimediaDataId() {
		return multimediaDataId;
	}

	public final void setMultimediaDataId(int value) {
		multimediaDataId = value;
	}

	/**
	 * 重传包总数
	 */
	private byte repassPacketsCount;

	public final byte getRepassPacketsCount() {
		return repassPacketsCount;
	}

	public final void setRepassPacketsCount(byte value) {
		repassPacketsCount = value;
	}

	/**
	 * 重传包ID列表
	 */
	private java.util.ArrayList<Short> repassPacketsNo;

	public final java.util.ArrayList<Short> getRepassPacketsNo() {
		return repassPacketsNo;
	}

	public final void setRepassPacketsNo(java.util.ArrayList<Short> value) {
		repassPacketsNo = value;
	}

	@Override
	public final byte[] writeToBytes() {
		ByteBuffer bytes = new ByteBuffer();
		bytes.put((byte) (getMultimediaDataId() >> 24));
		bytes.put((byte) (getMultimediaDataId() >> 16));
		bytes.put((byte) (getMultimediaDataId() >> 8));
		bytes.put((byte) getMultimediaDataId());
		bytes.put(getRepassPacketsCount());
		if (this.repassPacketsNo != null) {
			for (short repassPacketNo : getRepassPacketsNo()) {
				bytes.put((byte) (repassPacketNo >> 8));
				bytes.put((byte) repassPacketNo);
			}
		}
		return bytes.array();
	}

	@Override
	public final void readFromBytes(byte[] bytes) {
		setMultimediaDataId((int) ((bytes[0] << 24) + (bytes[1] << 16) + (bytes[2] << 8) + bytes[3]));
		setRepassPacketsCount(bytes[4]);
		setRepassPacketsNo(new java.util.ArrayList<>(getRepassPacketsCount()));
		int pos = 5;
		while (pos < bytes.length) {
			getRepassPacketsNo().add((short) ((bytes[pos] << 8) + bytes[1]));
			pos += 2;
		}
	}
}