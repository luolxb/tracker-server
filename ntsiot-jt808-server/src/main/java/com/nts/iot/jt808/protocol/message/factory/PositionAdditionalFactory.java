/*******************************************************************************
 * @(#)PositionAdditionalFactory.java 2019-04-28
 *
 * Copyright 2019 Liaoning RNS Technology Co., Ltd.All rights reserved.
 * RNS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.nts.iot.jt808.protocol.message.factory;

import com.nts.iot.jt808.protocol.*;
import com.nts.iot.jt808.protocol.base.IPositionAdditionalItem;
import com.nts.iot.jt808.utils.BitConverter;
import com.nts.iot.jt808.protocol.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author <a href="mailto:gaop@rnstec.com">Gaven</a>
 * @version ntsiot 1.0 $ 2019-04-28 19:19
 */
@Slf4j
public class PositionAdditionalFactory {

    public static IPositionAdditionalItem createPositionalFactory(int additionalId, int length, byte[] bytes) {
        IPositionAdditionalItem additional = null;
        switch (additionalId) {
            case 0x01:
                additional = new PostitionAdditional_Mileage();
                additional.readFromBytes(bytes);
                break;
            case 0x02:
                additional = new PostitionAdditional_Oil();
                additional.readFromBytes(bytes);
                break;
            case 0x03:
                additional = new PostitionAdditional_RecorderSpeed();
                additional.readFromBytes(bytes);
                break;
            case 0x04:
                additional = new PostitionAdditional_AlarmEventId();
                additional.readFromBytes(bytes);
                break;
            case 0x11:
                additional = new PostitionAdditional_OverSpeedAlarmAdditional();
                additional.readFromBytes(bytes);
                break;
            case 0x12:
                additional = new PostitionAdditional_InOutAreaAlarmAdditional();
                additional.readFromBytes(bytes);
                break;
            case 0x13:
                additional = new PostitionAdditional_RouteDriveTimeAlarmAdditional();
                additional.readFromBytes(bytes);
            case 0x25:
                additional = new PostitionAdditional_Signal();
                additional.readFromBytes(bytes);
                break;
            case 0x30:
                additional = new PostitionAdditional_Signal();
                additional.readFromBytes(bytes);
                log.info("======>无线信号参数:"+ BitConverter.toUInt32(bytes[0]));
                break;
            case 0x31:
                additional = new PostitionAdditional_Satellite();
                additional.readFromBytes(bytes);
                log.info("======>定位卫星类型:"+BitConverter.toUInt32(bytes[0]));

                break;
                // TODO 225编号的协议暂时没有用
            case 0xe1:
                log.info("======>开锁次数:（不可用）");
                break;
            case 0xe2:
                additional = new PositionAdditional_LockState();
                additional.readFromBytes(bytes);
                break;
                // TODO 改成外部电池电量信息
            case 0xe3:
                additional = new PostitionAdditional_OutsideVoltage();
                additional.readFromBytes(bytes);
                break;
                // TODO 改成内部电池电量信息
            case 0xe5:
                additional = new PostitionAdditional_InsideVoltage();
                additional.readFromBytes(bytes);
                break;
            default:
                break;
        }
        return additional;
    }

}
