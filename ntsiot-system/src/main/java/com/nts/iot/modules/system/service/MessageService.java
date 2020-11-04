package com.nts.iot.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nts.iot.modules.system.model.Message;

import java.util.List;

/**
 * @Author: zzm@rnstec.com
 * @Date: 2019/6/12 09:33
 * @Description:
 */
public interface MessageService extends IService<Message> {

    List<Message> getByUserId(String userId);

    /**
     * 发送消息
     * @param userId
     * @param msgType
     * @param msgTypeId
     * @param content
     */
    void sendMsg(Long userId, String msgType, String msgTypeId, String content);
}
