package com.nts.iot.jt808.protocol;

import com.nts.iot.jt808.protocol.base.IMessageBody;
import com.nts.iot.jt808.protocol.buffer.ByteBuffer;
import com.nts.iot.jt808.protocol.message.factory.T808MessageFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

import static com.nts.iot.jt808.protocol.message.T808Message.REQUEST_LOCATION;

/**
 * 批量位置信息汇报
 */
@Slf4j
public class JT_0202 implements IMessageBody {


    /**
     * 当前报文中的地址信息总个数
     */
    private int totalCount;//最多不会超过12x255个

    private LinkedList<JT_0200> jt_0200List;

    public LinkedList<JT_0200> getJt_0200List() {
        return jt_0200List;
    }

    public void setJt_0200List(LinkedList<JT_0200> jt_0200List) {
        this.jt_0200List = jt_0200List;
    }


    public final int getTotalCount() {
        return totalCount;
    }

    public final void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public final byte[] writeToBytes() {
        ByteBuffer buff = new ByteBuffer();
        buff.put(getTotalCount());
        if (getJt_0200List() != null) {
            int size = getJt_0200List().size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    buff.put(i);
                    byte[] bytes = getJt_0200List().get(i).writeToBytes();
                    buff.put(bytes.length);
                    buff.put(bytes);
                }
            }
        }
        return buff.toByteArrayAndRelease();
    }

    @Override
    public final void readFromBytes(byte[] bytes) {

        jt_0200List = new LinkedList<>();
        ByteBuffer buff = new ByteBuffer(bytes);
        try {
            setTotalCount(buff.getShort());
            //int total = getTotalCount();
            while (buff.hasRemain()) {
                int num = buff.getUnsignedByte();
                int locationInfolen = buff.getUnsignedByte();
                byte[] locationInfo = buff.gets(locationInfolen);
                JT_0200 jt_0200 = (JT_0200) T808MessageFactory.create(REQUEST_LOCATION, locationInfo);
                jt_0200List.add(jt_0200);
            }
            setJt_0200List(jt_0200List);
        } finally {
            buff.release();
        }

    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
//        sBuilder.append(String.format(
//                "当前报文中含位置信息的总个数：%1$s",
//                getTotalCount());
        return sBuilder.toString();
    }

}
