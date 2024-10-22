package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.game.tictactoe.dto.TicTacToeDTO;
import com.kkowbel.oneVone.user.ConnectedUserService;
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
    private final Map<String, TicTacToe> activeGames = new HashMap<>();

    @Override
    @Transactional
    public TicTacToe createGame(String player1) {
        if (userService.isUsernameAvailable(player1))
            throw new UsernameDontExistsException("Username '" + player1 + "' does not exist");
        TicTacToe newGame = new TicTacToe(player1);
        activeGames.put(newGame.getGameId(), newGame);
        saveNewGame(newGame);
        sendGameToUsers(newGame);
        return newGame;
    }

    @Override
    public TicTacToe joinGame(String gameId, String player2) {
        return null;
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

    private void saveNewGame(TicTacToe game) {
        activeGames.put(game.getGameId(), game);
        repository.save(game);
    }

    private void sendGameToUsers(TicTacToe game) {
        webSocketMessaging.sendMessageToActiveUsers(new TicTacToeDTO(game), "/games");
    }

}
