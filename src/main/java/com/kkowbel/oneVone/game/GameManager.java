package com.kkowbel.oneVone.game;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameManager {

    private final List<GameService<?>> gameServices;

    public GameManager(List<GameService<?>> gameServices) {
        this.gameServices = gameServices;
    }

    List<GameDTO> getAllActiveGames() {
        List<GameDTO> activeGames = new ArrayList<>();
        for (GameService<?> service : gameServices) {
            activeGames.addAll(service.getActiveGames());
        }
        return activeGames;
    }
}
