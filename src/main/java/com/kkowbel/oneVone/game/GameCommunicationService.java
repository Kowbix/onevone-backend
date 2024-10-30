package com.kkowbel.oneVone.game;

import com.kkowbel.oneVone.chat.gameChat.GameChatMessage;
import com.kkowbel.oneVone.chat.gameChat.GameChatService;
import com.kkowbel.oneVone.chat.gameChat.GameMessageType;
import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameCommunicationService {

    private final WebSocketMessaging webSocketMessaging;
    private final GameChatService gameChatService;

    public void sendGameUpdateToOpponent(Game game) {
        String messagePath = game.getPlayingPath();
        webSocketMessaging.sendNotification(game, messagePath);
    }

    public void sendGameToUsers(Game game) {
        webSocketMessaging.sendNotification(game.toDTO(), "/games");
    }


    public void sendUserInfoToGameChat(String player, String gameId, String gameName, GameActionType actionType) {
        String gameMessage = actionType.getActionMessage();
        gameChatService.sendMessage(new GameChatMessage(
                gameMessage,
                player,
                gameId,
                gameName,
                GameMessageType.SERVER_INFO
        ));
    }

}
