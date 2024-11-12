package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TicTacToeRepository extends JpaRepository<TicTacToe, String> {

    @Query("SELECT t FROM TicTacToe t WHERE t.status IN :statuses")
    List<TicTacToe> findAllByStatuses(@Param("statuses") List<GameStatus> statuses);
}
