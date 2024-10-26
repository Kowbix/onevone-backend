package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.Game;
import com.kkowbel.oneVone.game.GameDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicTacToe extends Game {

    private final String gameName = "tictactoe";
    private String turn;
    private String[][] board;

    public TicTacToe(String player1) {
        super(player1);
        this.turn = player1;
        this.board = initNewBoard();
    }

    @Override
    public GameDTO toDTO() {
        return new GameDTO(
                getGameId(),
                getStatus(),
                getPlayer1(),
                getPlayer2()
        );
    }

    @Override
    public String getPlayingPath() {
        return "/game/" + getGameName() + "/" + getGameId();
    }

    private String[][] initNewBoard() {
        String[][] newBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard[i][j] = " ";
            }
        }
        return newBoard;
    }
}
