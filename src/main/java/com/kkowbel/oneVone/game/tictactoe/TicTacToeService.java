package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.game.tictactoe.dto.TicTacToeDTO;

import java.util.List;

interface TicTacToeService {

    TicTacToe createGame(String player1);
    TicTacToe joinGame(String gameId, String player2);
    TicTacToe playTurn(TicTacToeGameMove move);
    TicTacToe leaveGame(String player);
    List<TicTacToeDTO> getActiveGames();

}
