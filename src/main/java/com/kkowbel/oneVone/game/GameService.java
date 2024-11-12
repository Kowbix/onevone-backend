package com.kkowbel.oneVone.game;

import java.util.List;

public interface GameService<T> {

    String createGame(String player1);
    T getGameById(String id);
    void joinGame(String gameId, String player);
    List<GameDTO> getActiveGames();
    void disconnectFromGame(String gameId, String username);
}
