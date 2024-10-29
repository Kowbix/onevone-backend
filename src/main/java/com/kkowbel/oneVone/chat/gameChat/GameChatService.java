package com.kkowbel.oneVone.chat.gameChat;

import com.kkowbel.oneVone.game.GameService;
import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import org.springframework.stereotype.Service;

@Service
public class GameChatService {

    private final WebSocketMessaging webSocketMessaging;
    private final GameService gameService;

    public GameChatService(WebSocketMessaging webSocketMessaging, GameService gameService) {
        this.webSocketMessaging = webSocketMessaging;
        this.gameService = gameService;
    }

    public void sendUserMessage(GameChatMessageRequestDTO dto) {
        GameChatMessage message = GameChatMessageFactory.createGameChatMessage(
                dto, GameMessageType.USER_MESSAGE
        );
        String path = message.getMessagePath();
        webSocketMessaging.sendMessageToActiveUsers(message, path);
//        TODO: ADD controller + tictactoeService
    }


}
