package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.GameDoesNotExistsException;
import com.kkowbel.oneVone.game.*;
import com.kkowbel.oneVone.user.UserService;
import com.kkowbel.oneVone.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
class TicTacToeService implements GameService<TicTacToe> {

    private final TicTacToeRepository repository;
    private final UserService userService;
    private final TicTacToePlayerService playerService;
    private final GameCommunicationService communicationService;
    private final TicTacToeLogic gameLogicService;



    @Override
    @Transactional
    public String createGame(String player) {
        TicTacToe newGame = new TicTacToe(player);
        repository.save(newGame);
        communicationService.sendGameToUsers(newGame);
        userService.updateUserStatus(player, UserStatus.IN_GAME);
        return newGame.getGameId();
    }

    @Override
    public TicTacToe getGameById(String id) {
        return repository.findById(id)
                .orElseThrow(()-> new GameDoesNotExistsException("Game '" + id +"' does not exist"));
    }

    @Override
    @Transactional
    public void joinGame(String gameId, String player) {
        TicTacToe game = getGameById(gameId);
        addPlayer(game, player);
    }

    @Transactional
    public void playTurn(TicTacToeMoveDTO move, String player) {
        TicTacToe game = getGameById(move.getGameId());
        performMove(game, move, player);
    }

    @Override
    public List<GameDTO> getActiveGames() {
        List<TicTacToe> games = repository.findAllByStatuses(
                Arrays.asList(
                        GameStatus.IN_PROGRESS,
                        GameStatus.WAITING_FOR_PLAYER
                )
        );
        return games.stream()
                .map(TicTacToe::toDTO)
                .toList();
    }

    @Override
    public void disconnectFromGame(String gameId, String player) {
        TicTacToe game = getGameById(gameId);
        abandonGame(game, player);
    }

    private void addPlayer(TicTacToe game, String player) {
        playerService.addPlayerToGame(game, player);
        game.setStatus(GameStatus.IN_PROGRESS);
        updateGame(game);
    }

    private void performMove(TicTacToe game, TicTacToeMoveDTO move, String player) {
        String playerMark = playerService.getPlayerMark(player, game);
        gameLogicService.makeMove(game, move, playerMark);
        updateGame(game);
    }

    private void abandonGame(TicTacToe game, String player) {
        if (game.getStatus() != GameStatus.WON &&
                game.getStatus() != GameStatus.DRAW) {
            playerService.removePlayerFromGame(game, player);
            game.setStatus(GameStatus.ABANDONED);
            updateGame(game);
        }
    }

    private void updateGame(TicTacToe game) {
        repository.save(game);
        sendGameUpdate(game);
    }

    private void sendGameUpdate(TicTacToe game) {
        communicationService.sendGameUpdateToOpponent(game);
        communicationService.sendGameToUsers(game);
    }
}
