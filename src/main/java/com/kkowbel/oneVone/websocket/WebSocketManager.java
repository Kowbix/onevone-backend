package com.kkowbel.oneVone.websocket;

import com.kkowbel.oneVone.user.ConnectedUser;
import com.kkowbel.oneVone.user.ConnectedUserService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketManager {

    private final WebSocketMessaging webSocketMessaging;
    private final ConnectedUserService connectedUserService;

    public WebSocketManager(WebSocketMessaging webSocketMessaging,
                            ConnectedUserService connectedUserService) {
        this.webSocketMessaging = webSocketMessaging;
        this.connectedUserService = connectedUserService;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ConnectedUser user = connectedUserService.disconnect(username);
            webSocketMessaging.sendConnectedUserToSubscribers(user);
        }
    }


}
