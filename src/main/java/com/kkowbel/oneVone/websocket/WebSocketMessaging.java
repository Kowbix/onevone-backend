package com.kkowbel.oneVone.websocket;

import com.kkowbel.oneVone.user.ConnectedUser;
import com.kkowbel.oneVone.user.ConnectedUserService;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketMessaging {

    private final SimpMessagingTemplate messagingTemplate;


    public WebSocketMessaging(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
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
