package com.dzk.web.webSocket;

import org.apache.shiro.SecurityUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author dzk
 * @date 2020/8/1 14:28
 * @description
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {
    public WebSocketInterceptor() {
        super();
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
         if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            if (session != null) {
                map.put("username", (String)SecurityUtils.getSubject().getPrincipal() /*session.getAttribute("username")*/);
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
