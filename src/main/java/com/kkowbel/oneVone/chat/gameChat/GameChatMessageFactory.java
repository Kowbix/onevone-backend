package com.kkowbel.oneVone.chat.gameChat;

class GameChatMessageFactory {

    public static GameChatMessage createGameChatMessage(GameChatMessageRequestDTO dto, GameMessageType type) {
        return new GameChatMessage(
                dto.getMessage(),
                dto.getSender(),
                dto.getGameId(),
                dto.getGameName(),
                type
        );
    }
}
