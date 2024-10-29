package com.kkowbel.oneVone.chat.gameChat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameChatMessageRequestDTO {

    private String message;
    private String sender;
    private String gameId;
    private String gameName;
}
