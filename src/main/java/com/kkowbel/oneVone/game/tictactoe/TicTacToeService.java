package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.game.tictactoe.dto.TicTacToeDTO;

import java.util.List;

interface TicTacToeService {

    String createGame(String player1);
    TicTacToe getActiveGameById(String id);
    void joinGame(String gameId, String player);
    TicTacToe playTurn(TicTacToeGameMove move);
    TicTacToe leaveGame(String player);
    List<TicTacToeDTO> getActiveGames();

}
