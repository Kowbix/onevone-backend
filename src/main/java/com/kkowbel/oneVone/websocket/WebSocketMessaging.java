package com.kkowbel.oneVone.websocket;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketMessaging {

    private final SimpMessagingTemplate messagingTemplate;


    public WebSocketMessaging(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void addUsernameToWebSocket(SimpMessageHeaderAccessor headerAccessor, String username) {
        headerAccessor.getSessionAttributes().put("username", username);
    }

    public void sendMessageToActiveUsers(Object object, String path) {
        messagingTemplate.convertAndSend("/topic" + path, object);
    }

}
