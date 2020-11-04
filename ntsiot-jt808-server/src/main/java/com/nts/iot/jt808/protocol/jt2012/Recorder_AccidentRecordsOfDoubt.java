package com.nts.iot.jt808.protocol.jt2012;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.utils.DateUtil;
import com.nts.iot.jt808.utils.StringUtil;
import com.nts.iot.jt808.protocol.jt2012.base.IRecorderDataBlock_2012;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Recorder_AccidentRecordsOfDoubt implements IRecorderDataBlock_2012 {

	private List<VehicleRecorder> vehicleRecorderList = new ArrayList<>();

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
		if (bytes != null) {
			for (int i = 0; i < bytes.length / 234; i++) {
				VehicleRecorder vr = new VehicleRecorder();
				byte[] questionable = new byte[234];
				System.arraycopy(bytes, 0 + 234 * i, questionable, 0, 234);
				byte[] time = new byte[6];
				System.arraycopy(questionable, 0 + 234 * i, time, 0, 6);
				Date beginTime = BitConverter.getDate(time, 0);
				vr.setStartTime(beginTime);
				
				byte[] nub = new byte[18];
				System.arraycopy(questionable, 6 + 234 * i, nub, 0, 18);
				String driverLicense = BitConverter.getString(nub);
				vr.setDriverLicense(driverLicense);
				if (driverLicense.length() == 15) {
					String add = "00H";
					driverLicense = driverLicense + add;
				}

				for (int j = 0; j < 100; j++) {
					SpeedRecorder sr = new SpeedRecorder();
					
					int s = BitConverter.toUInt32(questionable[24 + 2 * j]);
					String speed = "" + s;
					if (speed.length() < 3) {
						speed = StringUtil.leftPad(speed, 3, '0');
					}
					sr.setSpeed(s);
					
					int signal = questionable[25 + 2 * j];
					sr.setSignal(signal);

					String State = "";

					if ((signal & 0x80) == 0x80) {
						State = "1";
					} else {
						State = "0";
					}
					if ((signal & 0x40) == 0x40) {
						State += "1";
					} else {
						State += "0";
					}
					if ((signal & 0x20) == 0x20) {
						State += "1";
					} else {
						State += "0";
					}
					if ((signal & 0x10) == 0x10) {
						State += "1";
					} else {
						State += "0";
					}
					if ((signal & 8) == 8) {
						State += "1";
					} else {
						State += "0";
					}
					if ((signal & 4) == 4) {
						State += "1";
					} else {
						State += "0";
					}
					if ((signal & 2) == 2) {
						State += "1";
					} else {
						State += "0";
					}
					if ((signal & 0) == 0) {
						State += "1";
					} else {
						State += "0";
					}

					int milisecond = (int)(1000 * (0.2 + 0.2 * j));
					Date takeTime = DateUtil.getDate(beginTime, Calendar.MILLISECOND, milisecond);
					sr.setRecorderDate(takeTime);
					
					vr.getSpeedList().add(sr);
				}

				byte[] placeInfo = new byte[10];
				System.arraycopy(questionable, 224, placeInfo, 0, 10);
				int start = 224;
				int longitude = BitConverter.toUInt32(questionable, start);
				int latitude = BitConverter.toUInt32(questionable, start+4);
				int altitude = BitConverter.toUInt16(questionable, start+8);
				vr.setLatitude(latitude);
				vr.setLongitude(longitude);
				vr.setAltitude(altitude);
				vehicleRecorderList.add(vr);
			}
		}
	}
}
