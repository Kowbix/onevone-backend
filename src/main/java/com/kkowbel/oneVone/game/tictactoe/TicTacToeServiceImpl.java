package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.FullGameException;
import com.kkowbel.oneVone.exception.GameDontExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.game.tictactoe.dto.TicTacToeDTO;
import com.kkowbel.oneVone.user.ConnectedUserService;
import com.kkowbel.oneVone.user.ConnectedUserStatus;
import com.kkowbel.oneVone.websocket.WebSocketManager;
import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
class TicTacToeServiceImpl implements TicTacToeService {

    private final TicTacToeRepository repository;
    private final ConnectedUserService userService;
    private final WebSocketMessaging webSocketMessaging;

    @Override
    @Transactional
    public String createGame(String player1) {
        if (userService.isUsernameAvailable(player1))
            throw new UsernameDontExistsException("Username '" + player1 + "' does not exist");
        TicTacToe newGame = new TicTacToe(player1);
        saveNewGame(newGame);
        sendGameToUsers(newGame);
        userService.changeUserStatus(player1, ConnectedUserStatus.IN_GAME);
        return newGame.getGameId();
    }

    @Override
    public TicTacToe getActiveGameById(String id) {
        return repository.findById(id)
                .orElseThrow(()-> new GameDontExistsException("Game '" + id +"' does not exist"));
    }

    @Override
    public void joinGame(String gameId, String player) {
        TicTacToe game = getActiveGameById(gameId);
        setPlayer(game, player);
        game.setStatus(GameStatus.IN_PROGRESS);
        repository.save(game);
        sendGameUpdateToOpponent(game);
    }

    @Override
    public TicTacToe playTurn(TicTacToeGameMove move) {
        return null;
    }

    @Override
    public TicTacToe leaveGame(String player) {
        return null;
    }

    @Override
    public List<TicTacToeDTO> getActiveGames() {
        List<TicTacToe> games = repository.findAllByStatusNot(GameStatus.FINISHED);
        return games.stream()
                .map(TicTacToeDTO::new)
                .toList();
    }

    private boolean isWin(TicTacToe game) {
        return false;
    }

    private void setPlayer(TicTacToe game, String player) {
        if (game.getPlayer1() != null && game.getPlayer2() != null)
            throw new FullGameException("Game: '" + game.getGameId() + "' is full");

        if (game.getPlayer1() == null) game.setPlayer1(player);
        else game.setPlayer2(player);

    }

    private void saveNewGame(TicTacToe game) {
        repository.save(game);
    }

    private void sendGameUpdateToOpponent(TicTacToe game) {
        String messagePath = "/game/tictactoe/" + game.getGameId();
        webSocketMessaging.sendMessageToActiveUsers(game, messagePath);
        sendGameToUsers(game);
    }
    private void sendGameToUsers(TicTacToe game) {
        webSocketMessaging.sendMessageToActiveUsers(new TicTacToeDTO(game), "/games");
    }


}
