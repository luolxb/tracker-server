package com.nts.iot.modules.miniApp.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.qiniu.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * websocket 握手
 *
 * @author lihy
 * @version 2018/6/15
 */
@Slf4j
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        /* 验证token 思路
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");
        if (("lihaoyang").equals(token)) {
            return true;
        }else {
            return false;
        }*/
        //在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
        try {
            log.info("HttpHandshakeInterceptor.beforeHandshake start..." + JSON.toJSONString(attributes));
        } catch (Exception ex) {
            log.info("HttpHandshakeInterceptor.beforeHandshake error" + ex.getMessage());
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        //在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头
        try {
            log.info("HttpHandshakeInterceptor.afterHandshake start...");
        } catch (Exception e) {
            log.info("HttpHandshakeInterceptor.afterHandshake error" + e.getMessage());
        }
    }

}
