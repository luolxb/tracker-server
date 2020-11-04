package com.nts.iot.jt808.protocol.jt2012;


import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.ArrayList;
import java.util.List;

public class Recorder_TimeOutDrivingRecord implements IRecorderDataBlock_2012 {
	private List<TiredDrivingRecord> drivingRecordList = new ArrayList<>();

	@Override
	public final byte getCommandWord() {
		return 0x06;
	}

	@Override
	public final short getDataLength() {
		return 87;
	}

	@Override
	public final byte[] writeToBytes() {
		byte[] bytes = null;
		return bytes;
	}

	@Override
	public final void readFromBytes(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		if (bytes != null) {
			for (int i = 0; i < bytes.length / 50; i++) {
				byte[] nub = new byte[18];
				System.arraycopy(bytes, 0 + 50 * i, nub, 0, 18);

				String driverNub = BitConverter.getString(nub);
				if (driverNub.length() == 15) {
					String add = "00H";
					driverNub = driverNub + add;
				}

				String licenseNo = driverNub;
				byte[] ContinuousDrivingBeginTime = new byte[6];
				System.arraycopy(bytes, 18 + 50 * i,
						ContinuousDrivingBeginTime, 0, 6);
				String beginTime = new java.util.Date(java.util.Date.parse("20"
						+ String.format("%02X", ContinuousDrivingBeginTime[0])
						+ "-"
						+ String.format("%02X", ContinuousDrivingBeginTime[1])
						+ "-"
						+ String.format("%02X", ContinuousDrivingBeginTime[2])
						+ " "
						+ String.format("%02X", ContinuousDrivingBeginTime[3])
						+ ":"
						+ String.format("%02X", ContinuousDrivingBeginTime[4])
						+ ":"
						+ String.format("%02X", ContinuousDrivingBeginTime[5])))
						.toString();
				String strStartTime = beginTime;
				byte[] ContinuousDrivingEndTime = new byte[6];
				System.arraycopy(bytes, 24 + 50 * i, ContinuousDrivingEndTime,
						0, 6);
				String endTime = new java.util.Date(java.util.Date.parse("20"
						+ String.format("%02X", ContinuousDrivingEndTime[0])
						+ "-"
						+ String.format("%02X", ContinuousDrivingEndTime[1])
						+ "-"
						+ String.format("%02X", ContinuousDrivingEndTime[2])
						+ " "
						+ String.format("%02X", ContinuousDrivingEndTime[3])
						+ ":"
						+ String.format("%02X", ContinuousDrivingEndTime[4])
						+ ":"
						+ String.format("%02X", ContinuousDrivingEndTime[5])))
						.toString();
				String strEndTime = endTime;
				byte[] BeginTimePlace = new byte[10];
				System.arraycopy(bytes, 30 + 50 * i, BeginTimePlace, 0, 10);
				String beginPlace = GetPlaceInfo(BeginTimePlace);
				String strStartLocation = beginPlace;
				byte[] EndTimePlace = new byte[10];
				System.arraycopy(bytes, 40 + 50 * i, EndTimePlace, 0, 10);
				String endPlace = GetPlaceInfo(EndTimePlace);
				String strEndLocation = endPlace;
				this.drivingRecordList.add(new TiredDrivingRecord(licenseNo,
						strStartTime, strEndTime, strStartLocation,
						strEndLocation));

			}
		}

	}

	private String GetPlaceInfo(byte[] placeInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append(""
				+ (int) ((int) (placeInfo[0] << 24)
						+ (int) (placeInfo[1] << 16)
						+ (int) (placeInfo[2] << 8) + (int) (placeInfo[3]))
				* 0.0001);

		sb.append(""
				+ (int) ((int) (placeInfo[4] << 24)
						+ (int) (placeInfo[5] << 16)
						+ (int) (placeInfo[6] << 8) + (int) (placeInfo[7]))
				* 0.0001);

		sb.append(""
				+ (int) ((int) (placeInfo[8] << 8) + (int) (placeInfo[9])));

		return sb.toString();
	}

	public List<TiredDrivingRecord> getDrivingRecordList() {
		return drivingRecordList;
	}

	public void setDrivingRecordList(List<TiredDrivingRecord> drivingRecordList) {
		this.drivingRecordList = drivingRecordList;
	}
}
