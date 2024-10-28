package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.FullGameException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class TicTacToePlayerService {

    void setPlayer(TicTacToe game, String player) {
        validateGameNotFull(game);
        assignPlayerToGame(game, player);
    }

    String getPlayerMark(String username, TicTacToe game) {
        validatePlayerInGame(username, game);
        return username.equals(game.getPlayer1()) ? "X" : "O";
    }

    void removeUserFromGame (TicTacToe game, String player) {
        validatePlayerInGame(player, game);
        if (Objects.equals(game.getPlayer1(), player)) game.setPlayer1(null);
        else if (Objects.equals(game.getPlayer2(), player)) game.setPlayer2(null);
    }

    private void validatePlayerInGame(String username, TicTacToe game) {
        if (!username.equals(game.getPlayer1()) && !username.equals(game.getPlayer2())) {
            throw new UsernameDontExistsException(
                    "Username '" + username + "' does not exist in game '" + game.getGameId() + "'"
            );
        }
    }

    private void validateGameNotFull(TicTacToe game) {
        if (game.getPlayer1() != null && game.getPlayer2() != null)
            throw new FullGameException("Game: '" + game.getGameId() + "' is full");
    }

    private void assignPlayerToGame(TicTacToe game, String player) {
        if (game.getPlayer1() == null) {
            game.setPlayer1(player);
        } else {
            game.setPlayer2(player);
        }
    }



}
