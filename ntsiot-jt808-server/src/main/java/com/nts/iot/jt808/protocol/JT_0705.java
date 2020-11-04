package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.utils.StringUtil;
import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * can总线数据上传
 */
public class JT_0705 implements IMessageBody {

    private short itemNum;

    private String recvTime;

    private List<CanDataItem> canDataList = new ArrayList<>();

    @Override
    public final byte[] writeToBytes() {
        return null;
    }

    @Override
    public final void readFromBytes(byte[] bytes) {
        ByteBuffer buff = new ByteBuffer(bytes);

        itemNum = buff.getShort();
        recvTime = buff.getBcdString(5);

        for (int k = 0; k < itemNum; k++) {
            CanDataItem ci = new CanDataItem();
            int tempId = buff.getInt();
            String strHex = Integer.toBinaryString(tempId);
            strHex = StringUtil.leftPad(strHex, 32, '0');
            ci.setChannel(Byte.parseByte(strHex.substring(0, 1)));
            ci.setFrameType(Byte.parseByte(strHex.substring(1, 2)));
            ci.setDataWay(Byte.parseByte(strHex.substring(2, 3)));
            String strCanId = strHex.substring(3);
            ci.setCanId(Integer.valueOf(strCanId, 2));
            ci.setCanData(buff.gets(8));
            canDataList.add(ci);
        }
    }

    public short getItemNum() {
        return itemNum;
    }

    public void setItemNum(short itemNum) {
        this.itemNum = itemNum;
    }

    public String getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(String recvTime) {
        this.recvTime = recvTime;
    }

    public List<CanDataItem> getCanDataList() {
        return canDataList;
    }

    public void setCanDataList(List<CanDataItem> canDataList) {
        this.canDataList = canDataList;
    }


}