package com.nts.iot.modules.monitor.config;

import com.nts.iot.modules.monitor.domain.LogMessage;
import com.nts.iot.modules.miniApp.config.HttpHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 配置WebSocket消息代理端点，即stomp服务端
 *
 * @author jie
 * @reference https://cloud.tencent.com/developer/article/1096792
 * @date 2018-12-24
 */
@Slf4j
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
        registry.addEndpoint("/gs-guide-websocket").addInterceptors(new HttpHandshakeInterceptor()).setAllowedOrigins("*");
        registry.addEndpoint("/bike-monitor")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置了一个简单的消息代理，如果不重载，默认情况下回自动配置一个简单的内存消息代理，用来处理以"/topic"为前缀的消息。这里重载configureMessageBroker()方法，
     * 消息代理将会处理前缀为"/topic"和"/queue"的消息。
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 这句话表示在topic和user这两个域上可以向客户端发消息。
        config.enableSimpleBroker("/topic", "/user")
                .setHeartbeatValue(new long[]{10000L, 10000L})
                .setTaskScheduler(new DefaultManagedTaskScheduler());
        // 这句话表示客户单向服务器端发送时的主题上面需要加"/app"作为前缀。
        config.setApplicationDestinationPrefixes("/app");
        // 这句话表示给指定用户发送一对一的主题前缀是"/user"。
        config.setUserDestinationPrefix("/user");
    }

   /* @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //registration.interceptors(new ChannelInterceptor());
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("configureClientInboundChannel preSend start！");
                //log.info("configureClientOutboundChannel...." + message);
                //System.out.println("send: " + message);
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
                    if (raw instanceof Map) {
                        Object name = ((Map) raw).get("name");
                        if (name instanceof LinkedList) {
                            try {
                                log.info("ChannelInterceptor preSend....." + ((LinkedList) name).get(0).toString());
                            } catch (Exception ex) {
                                log.info("ChannelInterceptor preSend error！");
                            }

                            // 设置当前访问器的认证用户
                            // accessor.setUser(new User(((LinkedList) name).get(0).toString()));
                        }
                    }
                }
                return super.preSend(message, channel);
            }
        });
    }*/

    /**
     * @return
     * @Title: createUserInterceptor
     * @Description: 将客户端渠道拦截器加入spring ioc容器
     */
    /*@Bean
    public ChannelInterceptor createUserInterceptor() {
        return new ChannelInterceptor();
    }*/
   /* @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("configureClientOutboundChannel...." + message);
                //System.out.println("send: " + message);
                return super.preSend(message, channel);
            }
        });
    }*/

    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        LogMessage log = LoggerQueue.getInstance().poll();
                        if (log != null) {
                            // 格式化异常堆栈信息
                            if ("ERROR".equals(log.getLevel()) && "com.zhengjie.common.exception.handler.GlobalExceptionHandler".equals(log.getClassName())) {
                                log.setBody("<pre>" + log.getBody() + "</pre>");
                            }
                            if (log.getClassName().equals("jdbc.resultsettable")) {
                                log.setBody("<br><pre>" + log.getBody() + "</pre>");
                            }
                            // todo 暂时关闭
//                            if(messagingTemplate!=null){
//                                messagingTemplate.convertAndSend("/topic/logMsg",log);
//                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(runnable);
    }
}
