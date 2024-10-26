package com.kkowbel.oneVone.game.tictactoe;

import lombok.*;

@Getter
@Setter
class TicTacToeMoveDTO {
    private String gameId;
    private int row;
    private int col;
    private boolean isWin;
}
