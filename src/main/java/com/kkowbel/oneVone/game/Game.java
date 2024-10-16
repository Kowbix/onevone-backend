package com.kkowbel.oneVone.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class Game {

    @Id
    private String gameId;
    private String player1;
    private String player2;
    private String winner;

    private LocalDate gameDate;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    public Game(String player1, String player2) {
        this.gameId = UUID.randomUUID().toString();
        this.player1 = player1;
        this.player2 = player2;
        this.gameDate = LocalDate.now();
        this.status = GameStatus.WAITING_FOR_PLAYER;
    }
}
