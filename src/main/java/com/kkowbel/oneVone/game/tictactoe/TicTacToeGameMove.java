package com.kkowbel.oneVone.game.tictactoe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class TicTacToeGameMove {
    private String gameId;
    private String player;
    private int x;
    private int y;
    private boolean isWin;
}
