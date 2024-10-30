package com.kkowbel.oneVone.chat.gameChat;

class GameChatMessageFactory {

    public static GameChatMessage createGameChatMessage(GameChatMessageRequestDTO dto, GameMessageType type, String sender) {
        return new GameChatMessage(
                dto.getMessage(),
                sender,
                dto.getGameId(),
                dto.getGameName(),
                type
        );
    }
}
