package com.kkowbel.oneVone.game;

import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMessagingService {

    private final WebSocketMessaging webSocketMessaging;

    public void sendGameUpdateToOpponent(Game game) {
        String messagePath = game.getPlayingPath();
        webSocketMessaging.sendMessageToActiveUsers(game, messagePath);
        sendGameToUsers(game);
    }

    public void sendGameToUsers(Game game) {
        webSocketMessaging.sendMessageToActiveUsers(game.toDTO(), "/games");
    }
}
