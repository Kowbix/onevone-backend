package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TicTacToeRepository extends JpaRepository<TicTacToe, String> {

    @Query("SELECT t from TicTacToe t where t.status <> :status")
    List<TicTacToe> findAllByStatusNot(GameStatus status);
}
