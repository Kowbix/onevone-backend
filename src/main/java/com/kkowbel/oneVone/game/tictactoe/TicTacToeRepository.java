package com.kkowbel.oneVone.game.tictactoe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicTacToeRepository extends JpaRepository<TicTacToe, String> {
}