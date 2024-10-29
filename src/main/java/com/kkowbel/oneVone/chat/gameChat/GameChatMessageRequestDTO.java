package com.kkowbel.oneVone.chat.gameChat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class GameChatMessageRequestDTO {

    private String message;
    private String sender;
    private String gameId;
    private String gameName;


}
