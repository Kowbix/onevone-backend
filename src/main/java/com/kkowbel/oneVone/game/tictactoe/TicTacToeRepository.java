package com.kkowbel.oneVone.game.tictactoe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TicTacToeRepository extends JpaRepository<TicTacToe, String> {
}
