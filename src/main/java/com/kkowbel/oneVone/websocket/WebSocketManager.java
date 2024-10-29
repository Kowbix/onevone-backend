package com.kkowbel.oneVone.websocket;

import com.kkowbel.oneVone.user.User;
import com.kkowbel.oneVone.user.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
public class WebSocketManager {

    private final WebSocketMessaging webSocketMessaging;
    private final UserService userService;

    public WebSocketManager(WebSocketMessaging webSocketMessaging,
                            UserService userService) {
        this.webSocketMessaging = webSocketMessaging;
        this.userService = userService;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");

        if (username != null) {
            User user = userService.disconnect(username);
            webSocketMessaging.sendNotification(user, "/users");
        }
    }


}
