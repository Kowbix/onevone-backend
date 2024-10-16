package com.kkowbel.oneVone.tictactoe;

import com.kkowbel.oneVone.game.Game;
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

    private String turn;
    private String[][] board;

    public TicTacToe(String player1, String player2) {
        super(player1, player2);
        this.turn = player1;
        this.board = initNewBoard();
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