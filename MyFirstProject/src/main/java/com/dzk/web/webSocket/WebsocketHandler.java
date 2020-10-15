package com.dzk.web.webSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dzk
 * @date 2020/8/1 14:22
 * @description
 */

public class WebsocketHandler extends TextWebSocketHandler {
    //在线用户列表
    private static final Map<String, WebSocketSession> users;
    //用户标识
    private static final String CLIENT_ID = "username";
    static {
        users = new ConcurrentHashMap<String,WebSocketSession>(30);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
         String username= getClientId(session);
        if (username != null) {
            users.put(username, session);
           /* session.sendMessage(new TextMessage("成功建立socket连接"));*/
            /*System.out.println(username);
            System.out.println(session);*/
        }
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        /* System.out.println(message.getPayload());*/
        WebSocketMessage message1 = new TextMessage("server:"+message);
        try {
            session.sendMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (users.get(clientId) == null) {
            /*System.out.println("1313313");*/
            return false;
        }
        WebSocketSession session = users.get(clientId);
       /* System.out.println("sendMessage:" + session);*/
        if (!session.isOpen()) {
           /* System.out.println("7657");*/
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            String clientId = (String) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
          String msg = message.toString();
          String userId = this.getClientId(session);
          System.err.println("该"+userId+"用户发送的消息是："+msg);
          message = new TextMessage("服务端已经接收到消息，msg="+msg);
          session.sendMessage(message);
    }
}
