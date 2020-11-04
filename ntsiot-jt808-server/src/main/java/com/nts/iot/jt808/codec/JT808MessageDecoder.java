/*******************************************************************************
 * @(#)JT808MessageDecoder.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.codec;

import com.nts.iot.jt808.protocol.message.T808Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 *     808协议解析器，专用于nettry server
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 14:07
 */
@Slf4j
public class JT808MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {

        if (byteBuf == null) {
            return;
        }
        System.out.println("channelHandlerContext:" + channelHandlerContext);
//        System.out.println("-------recv data:"+byteBuf2String(byteBuf));
        byteBuf.markReaderIndex();
        while (byteBuf.isReadable()) {
            byteBuf.markReaderIndex();
            int packetBeginIndex = byteBuf.readerIndex();
            byte tag = byteBuf.readByte();
            // 搜索包的开始位置
            if (tag == 0x7E && byteBuf.isReadable()) {
                tag = byteBuf.readByte();
                // 防止是两个0x7E,取后面的为包的开始位置
                // 寻找包的结束
                while (tag != 0x7E) {
                    if (byteBuf.isReadable() == false) {
                        // 没有找到结束包，等待下一次包
                        byteBuf.resetReaderIndex();
                        log.error("半包");
                        return;
                    }
                    tag = byteBuf.readByte();
                }
                int pos = byteBuf.readerIndex();
                int packetLength = pos - packetBeginIndex;
                if (packetLength > 1) {
                    byte[] tmp = new byte[packetLength];
                    byteBuf.resetReaderIndex();
                    byteBuf.readBytes(tmp);
                    T808Message message = new T808Message();
                    message.readFromBytes(tmp);
                    out.add(message);
                    // return message;
                } else {
                    // 说明是两个0x7E
                    byteBuf.resetReaderIndex();
                    // 两个7E说明前面是包尾，后面是包头
                    byteBuf.readByte();
                }
            }
        }
    }


    private String byteBuf2String(ByteBuf buf) throws UnsupportedEncodingException {
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        //String body = new String(req,"UTF-8");
        String body = byte2hex(req);
        return body;
    }

    private static String byte2hex(byte [] buffer){
        String h = "";

        for(int i = 0; i < buffer.length; i++){
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            h = h + " "+ temp;
        }
        return h;

    }

}
