package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.FullGameException;
import com.kkowbel.oneVone.exception.UsernameDoesNotExistException;
import com.kkowbel.oneVone.game.GameActionType;
import com.kkowbel.oneVone.game.GameCommunicationService;
import com.kkowbel.oneVone.user.UserService;
import com.kkowbel.oneVone.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class TicTacToePlayerService {

    private final UserService userService;
    private final GameCommunicationService communicationService;

    void addPlayerToGame(TicTacToe game, String player) {
        validateGameNotFull(game);
        assignPlayerToGame(game, player);
        sendUserGameNotification(player, game, GameActionType.JOIN);
    }

    String getPlayerMark(String username, TicTacToe game) {
        validatePlayerInGame(username, game);
        return username.equals(game.getPlayer1()) ? PlayerMark.X.getMark() : PlayerMark.O.getMark();
    }

    void removePlayerFromGame(TicTacToe game, String player) {
        validatePlayerInGame(player, game);
        if (Objects.equals(game.getPlayer1(), player)) game.setPlayer1(null);
        else if (Objects.equals(game.getPlayer2(), player)) game.setPlayer2(null);
        updateUserStatusAndNotify(player, game, GameActionType.LEAVE);
    }

    void sendUserGameNotification(String player, TicTacToe game, GameActionType action) {
        communicationService.sendUserInfoToGameChat(
                player,
                game.getGameId(),
                game.getGameName(),
                action
        );
    }


    private void validatePlayerInGame(String username, TicTacToe game) {
        if (!username.equals(game.getPlayer1()) && !username.equals(game.getPlayer2())) {
            throw new UsernameDoesNotExistException(
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
        userService.updateUserStatus(player, UserStatus.IN_GAME);
    }

    private void updateUserStatusAndNotify(String player, TicTacToe game, GameActionType action) {
        userService.updateUserStatus(player, UserStatus.ONLINE);
        sendUserGameNotification(player, game, action);
    }


}
