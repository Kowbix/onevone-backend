package com.kkowbel.oneVone.chat.gameChat;

import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameChatService {

    private final WebSocketMessaging webSocketMessaging;

    public void sendMessage(GameChatMessageRequestDTO dto, String sender) {
        GameChatMessage message = GameChatMessageFactory.createGameChatMessage(
                dto, GameMessageType.USER_MESSAGE, sender
        );
        sendMessage(message);
    }


    public void sendMessage(GameChatMessage message) {
        String path = message.getMessagePath();
        webSocketMessaging.sendNotification(message, path);
    }


}
