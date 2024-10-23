package com.kkowbel.oneVone.game;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
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

    public Game(String player1) {
        this.gameId = UUID.randomUUID().toString();
        this.player1 = player1;
        this.player2 = null;
        this.winner = null;
        this.gameDate = LocalDate.now();
        this.status = GameStatus.WAITING_FOR_PLAYER;
    }

    public abstract boolean checkWinner();
}
