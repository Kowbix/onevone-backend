package com.kkowbel.oneVone.websocket;

import com.kkowbel.oneVone.user.ConnectedUser;
import com.kkowbel.oneVone.user.ConnectedUserService;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketManager {

    private final ConnectedUserService connectedUserService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketManager(
            @Lazy ConnectedUserService connectedUserService,
            SimpMessagingTemplate messagingTemplate)
    {
        this.connectedUserService = connectedUserService;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            ConnectedUser user = connectedUserService.disconnect(username);
            sendConnectedUserToSubscribers(user);
        }
    }

    public void sendConnectedUserToSubscribers(ConnectedUser user) {
        messagingTemplate.convertAndSend("/topic/users", user);
    }

    public void addUsernameToWebSocket(SimpMessageHeaderAccessor headerAccessor, String username) {
        headerAccessor.getSessionAttributes().put("username", username);
    }

    public void sendGameMessageToUser(JSONObject gameObject, String username, String gameName) {
        String path = getGameMessagePath(gameName);
        messagingTemplate.convertAndSendToUser(username, path, gameObject);
    }

    private String getGameMessagePath(String gameName) {
        return "/queue/" + gameName;
    }
}
