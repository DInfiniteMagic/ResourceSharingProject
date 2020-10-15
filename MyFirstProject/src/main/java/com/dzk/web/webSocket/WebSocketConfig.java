package com.dzk.web.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * @author dzk
 * @date 2020/8/1 14:19
 * @description
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    WebSocketHandler myHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
         webSocketHandlerRegistry.addHandler(myHandler, "/MessageHandler").addInterceptors(new WebSocketInterceptor());
         webSocketHandlerRegistry.addHandler(myHandler, "/socketJs/MessageHandler").addInterceptors(new WebSocketInterceptor()).withSockJS();
    }

    /*@Bean
    public WebSocketHandler myHandler() {
        return new WebsocketHandler();
    }*/
}
