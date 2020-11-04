package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.base.IMessageBody;

public class JT_0702 implements IMessageBody {

    /**
     * 1 插入 2 拔出
     */
    private byte cardState;

    /**
     * 插卡或拔卡时间 在状态为1时有效 YYMMDDHHmmss
     */
    private String operTime;

    /**
     * 读卡结果，0成功 其他失败
     */
    private byte readResult;

    /**
     * 驾驶员姓名长度
     */
    private byte driverNameLength;

    /**
     * 驾驶员姓名
     */
    private String driverName;

    /**
     * 从业资格证编码 长度20
     */
    private String certificationCode;

    /**
     * 发证机构名称长度
     */
    private byte agencyNameLength;

    /**
     * 发证机构名称
     */
    private String agencyName;

    /**
     * 证件有效期 YYYYMMDD
     */
    private String validateDate;

    /**
     * 2011旧版协议，如果是旧版协议，就采用旧的格式
     */
    private JT_0702_old oldDriverData;

    @Override
    public String toString() {
        if (oldDriverData != null) {
            return oldDriverData.toString();
        }
        StringBuilder sBuilder = new StringBuilder();
        if (this.readResult == 0) {
            sBuilder.append(String.format(
                    "司机名称：%1$s,IC卡状态：%2$s,从业资格证：%3$s,发证机构：%4$s, 操作时间:%s, 有效期:%s", getDriverName(),
                    this.cardState, getCertificationCode(), getAgencyName(), operTime, validateDate));
        } else {
            sBuilder.append(String.format("IC卡状态：%2$s,操作时间:%s", this.cardState, operTime));
        }
        return sBuilder.toString();
    }

    @Override
    public final byte[] writeToBytes() {
        return null;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        if (bytes.length > 62) {
            oldDriverData = new JT_0702_old();
            oldDriverData.readFromBytes(bytes);
            return;
        }
        this.cardState = (bytes[0]);
        this.operTime = "20" + String.format("%02X", bytes[1]) + "-"
                + String.format("%02X", bytes[2]) + "-"
                + String.format("%02X", bytes[3]) + " "
                + String.format("%02X", bytes[4]) + ":"
                + String.format("%02X", bytes[5]) + ":"
                + String.format("%02X", bytes[6]);

        //如果是拔卡，则没有下面的字段
        if (this.cardState == 2) {
            return;
        }
        this.readResult = bytes[7];
        //只有读卡成功，才有下面的字段
        if (this.readResult == 0) {
            driverNameLength = bytes[8];
            this.driverName = BitConverter.getString(bytes, 9, driverNameLength);
            this.certificationCode = BitConverter.getString(bytes, 9 + driverNameLength, 20);
            agencyNameLength = bytes[driverNameLength + 29];
            this.agencyName = BitConverter.getString(bytes, 30 + driverNameLength, agencyNameLength);
            int pos = driverNameLength + 29 + agencyNameLength;
            this.validateDate = String.format("%02X", bytes[pos + 1])
                    + "" + String.format("%02X", bytes[pos + 2]) + "-"
                    + String.format("%02X", bytes[pos + 3]) + "-"
                    + String.format("%02X", bytes[pos + 4]);
        }

    }

    public byte getCardState() {
        return cardState;
    }

    public void setCardState(byte cardState) {
        this.cardState = cardState;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public byte getReadResult() {
        return readResult;
    }

    public void setReadResult(byte readResult) {
        this.readResult = readResult;
    }

    public byte getDriverNameLength() {
        return driverNameLength;
    }

    public void setDriverNameLength(byte driverNameLength) {
        this.driverNameLength = driverNameLength;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(String certificationCode) {
        this.certificationCode = certificationCode;
    }

    public byte getAgencyNameLength() {
        return agencyNameLength;
    }

    public void setAgencyNameLength(byte agencyNameLength) {
        this.agencyNameLength = agencyNameLength;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public JT_0702_old getOldDriverData() {
        return oldDriverData;
    }

    public void setOldDriverData(JT_0702_old oldDriverData) {
        this.oldDriverData = oldDriverData;
    }
}
