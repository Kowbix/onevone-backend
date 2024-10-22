package com.kkowbel.oneVone.game.tictactoe.dto;

import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.game.tictactoe.TicTacToe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicTacToeDTO {

    private String gameId;
    private GameStatus status;
    private String player1;
    private String player2;

    public TicTacToeDTO(TicTacToe game) {
        this.gameId = game.getGameId();
        this.status = game.getStatus();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
    }

}
