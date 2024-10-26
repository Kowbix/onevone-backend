package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.FullGameException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import org.springframework.stereotype.Service;

@Service
class TicTacToePlayerService {

    void setPlayer(TicTacToe game, String player) {
        if (game.getPlayer1() != null && game.getPlayer2() != null)
            throw new FullGameException("Game: '" + game.getGameId() + "' is full");

        if (game.getPlayer1() == null) game.setPlayer1(player);
        else game.setPlayer2(player);
    }

    String getPlayerMark(String username, TicTacToe game) {
        String player1 = game.getPlayer1();
        String player2 = game.getPlayer2();
        if (!username.equals(player1) && !username.equals(player2))
            throw new UsernameDontExistsException(
                    "Username '" + username + "' does not exist in game '" +game.getGameId() + "'"
            );
        return username.equals(player1) ? "X" : "O";
    }

}
