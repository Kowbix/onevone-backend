package com.kkowbel.oneVone.websocket;

import com.kkowbel.oneVone.user.ConnectedUser;
import com.kkowbel.oneVone.user.ConnectedUserService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final ConnectedUserService connectedUserService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketEventListener(ConnectedUserService connectedUserService, SimpMessagingTemplate messagingTemplate) {
        this.connectedUserService = connectedUserService;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ConnectedUser user = connectedUserService.disconnect(username);
            messagingTemplate.convertAndSend("/topic/public", user);
        }
    }
}
