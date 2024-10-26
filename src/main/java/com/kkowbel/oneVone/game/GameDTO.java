package com.kkowbel.oneVone.game;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GameDTO {

    private String gameId;
    private String gameName;
    private GameStatus status;
    private String player1;
    private String player2;

    public GameDTO(String gameId,
                   String gameName,
                   GameStatus status,
                   String player1,
                   String player2) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.status = status;
        this.player1 = player1;
        this.player2 = player2;
    }
}
