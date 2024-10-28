package com.kkowbel.oneVone.game;

import java.util.List;

public interface GameService<T> {

    String createGame(String player1);
    T getGameById(String id);
    void joinToGame(String gameId, String player);
    List<GameDTO> getActiveGames();
    void disconnectFromTheGame(String gameId, String username);
}
