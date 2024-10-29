package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.GameDontExistsException;
import com.kkowbel.oneVone.game.GameDTO;
import com.kkowbel.oneVone.game.GameCommunicationService;
import com.kkowbel.oneVone.game.GameService;
import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.user.UserService;
import com.kkowbel.oneVone.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(()-> new GameDontExistsException("Game '" + id +"' does not exist"));
    }

    @Override
    @Transactional
    public void joinToGame(String gameId, String player) {
        TicTacToe game = getGameById(gameId);
        addPlayer(game, player);
    }

    public void playTurn(TicTacToeMoveDTO move, String username) {
        TicTacToe game = getGameById(move.getGameId());
        String playerMark = playerService.getPlayerMark(username, game);
        performMove(game, move, playerMark);
    }

    @Override
    public List<GameDTO> getActiveGames() {
        List<TicTacToe> games = repository.findAllByStatusNot(GameStatus.FINISHED);
        return games.stream()
                .map(TicTacToe::toDTO)
                .toList();
    }

    @Override
    public void disconnectFromTheGame(String gameId, String username) {
        TicTacToe game = getGameById(gameId);
        playerService.removeUserFromGame(game, username);
        endGame(game);

    }

    private void performMove(TicTacToe game, TicTacToeMoveDTO move, String playerMark) {
        gameLogicService.makeMove(game, move, playerMark);
        if(game.getWinner() != null) endGame(game);
        else updateGameStatusAfterMove(game);
    }

    private void endGame(TicTacToe game) {
        game.setStatus(GameStatus.FINISHED);
        repository.save(game);
        sendGameUpdate(game);
    }

    private void addPlayer(TicTacToe game, String username) {
        playerService.addPlayerToGame(game, username);
        game.setStatus(GameStatus.IN_PROGRESS);
        repository.save(game);
        sendGameUpdate(game);
    }

    private void sendGameUpdate(TicTacToe game) {
        communicationService.sendGameUpdateToOpponent(game);
        communicationService.sendGameToUsers(game);
    }

    private void updateGameStatusAfterMove(TicTacToe game) {
        repository.save(game);
        communicationService.sendGameUpdateToOpponent(game);
    }

}
