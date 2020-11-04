package com.nts.iot.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nts.iot.modules.system.dao.MessageMapper;
import com.nts.iot.modules.system.model.Message;
import com.nts.iot.modules.system.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zzm@rnstec.com
 * @Date: 2019/7/15 00:00
 * @Description:
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {


    @Override
    public List<Message> getByUserId(String userId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Long.valueOf(userId));
        queryWrapper.orderByDesc("create_time");
        return this.list(queryWrapper);
    }

    /**
     * 发送消息
     *
     * @param userId
     * @param msgType
     * @param msgTypeId
     * @param content
     */
    @Override
    public void sendMsg(Long userId, String msgType, String msgTypeId, String content) {
        /* 生成消息 */
        Message newMessage = new Message();
        newMessage.setId(null);
        newMessage.setContent(content);
        newMessage.setType(msgType);
        newMessage.setTypeId(msgTypeId);
        newMessage.setUserId(userId);
        newMessage.setCreateTime(System.currentTimeMillis());
        newMessage.setIsRead(false);
        this.save(newMessage);
    }
}
