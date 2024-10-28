package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.GameDontExistsException;
import com.kkowbel.oneVone.game.GameDTO;
import com.kkowbel.oneVone.game.GameMessagingService;
import com.kkowbel.oneVone.game.GameService;
import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.user.User;
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
    private final GameMessagingService messagingService;
    private final TicTacToeLogic gameLogicService;


    @Override
    @Transactional
    public String createGame(String player) {
        TicTacToe newGame = new TicTacToe(player);
        repository.save(newGame);
        messagingService.sendGameToUsers(newGame);
        userService.updateUserStatus(player, UserStatus.IN_GAME);
        return newGame.getGameId();
    }

    @Override
    public TicTacToe getGameById(String id) {
        return repository.findById(id)
                .orElseThrow(()-> new GameDontExistsException("Game '" + id +"' does not exist"));
    }

    @Override
    public void joinToGame(String gameId, String player) {
        TicTacToe game = getGameById(gameId);
        setPlayerAndStartGame(game, player);
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
        removePlayer(game, username);
        disconnectGame(game);
//        TODO: send message to opponent
    }

    private void disconnectGame(TicTacToe game) {
        game.setStatus(GameStatus.FINISHED);
        repository.save(game);
        messagingService.sendGameToUsers(game);
        messagingService.sendGameUpdateToOpponent(game);
    }

    private void removePlayer(TicTacToe game, String username) {
        playerService.removeUserFromGame(game, username);
        userService.updateUserStatus(username, UserStatus.ONLINE);
    }

    private void setPlayerAndStartGame(TicTacToe game, String player) {
        userService.updateUserStatus(player, UserStatus.IN_GAME);
        playerService.setPlayer(game, player);
        game.setStatus(GameStatus.IN_PROGRESS);
        repository.save(game);
        messagingService.sendGameUpdateToOpponent(game);
    }

    private void performMove(TicTacToe game, TicTacToeMoveDTO move, String playerMark) {
        gameLogicService.makeMove(game, move, playerMark);
        repository.save(game);
        messagingService.sendGameUpdateToOpponent(game);
        if(game.getWinner() != null) {
            messagingService.sendGameToUsers(game);
        }
    }

}
