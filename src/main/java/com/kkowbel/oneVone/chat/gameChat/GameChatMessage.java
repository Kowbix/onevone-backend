package com.kkowbel.oneVone.chat.gameChat;

import com.kkowbel.oneVone.chat.Message;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameChatMessage extends Message {

    private String gameId;
    private String gameName;

    @Enumerated(EnumType.STRING)
    private GameMessageType type;

    public GameChatMessage(String message, String sender, String gameId, String gameName, GameMessageType type) {
        super(message, sender);
        this.gameId = gameId;
        this.gameName = gameName;
        this.type = type;
    }



    @Override
    public String getMessagePath() {
        return "/game/chat/" + getGameName() + "/" + getGameId();
    }
}
