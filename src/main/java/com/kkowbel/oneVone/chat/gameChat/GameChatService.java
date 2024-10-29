package com.kkowbel.oneVone.chat.gameChat;

import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import org.springframework.stereotype.Service;

@Service
public class GameChatService {

    private final WebSocketMessaging webSocketMessaging;

    public GameChatService(WebSocketMessaging webSocketMessaging) {
        this.webSocketMessaging = webSocketMessaging;
    }

    public void sendUserMessage(GameChatMessageRequestDTO dto) {
        GameChatMessage message = GameChatMessageFactory.createGameChatMessage(
                dto, GameMessageType.USER_MESSAGE
        );
        String path = message.getMessagePath();
        webSocketMessaging.sendNotification(message, path);
//        TODO: ADD controller
    }

    public void sendGameInfoMessage(GameChatMessage message) {
        String path = message.getMessagePath();
        webSocketMessaging.sendNotification(message, path);
    }


}
