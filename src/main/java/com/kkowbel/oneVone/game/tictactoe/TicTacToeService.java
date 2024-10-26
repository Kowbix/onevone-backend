package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.exception.GameDontExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.game.GameDTO;
import com.kkowbel.oneVone.game.GameMessagingService;
import com.kkowbel.oneVone.game.GameService;
import com.kkowbel.oneVone.game.GameStatus;
import com.kkowbel.oneVone.user.ConnectedUserService;
import com.kkowbel.oneVone.user.ConnectedUserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class TicTacToeService implements GameService<TicTacToe> {

    private final TicTacToeRepository repository;
    private final ConnectedUserService userService;
    private final TicTacToePlayerService playerService;
    private final GameMessagingService messagingService;
    private final TicTacToeLogic gameLogicService;


    @Override
    @Transactional
    public String createGame(String player1) {
        if (userService.isUsernameAvailable(player1))
            throw new UsernameDontExistsException("Username '" + player1 + "' does not exist");
        TicTacToe newGame = new TicTacToe(player1);
        repository.save(newGame);
        messagingService.sendGameToUsers(newGame);
        userService.changeUserStatus(player1, ConnectedUserStatus.IN_GAME);
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
        playerService.setPlayer(game, player);
        game.setStatus(GameStatus.IN_PROGRESS);
        repository.save(game);
        messagingService.sendGameUpdateToOpponent(game);
    }

    public void playTurn(TicTacToeMoveDTO move, String username) {
        TicTacToe game = getGameById(move.getGameId());
        String playerMark = playerService.getPlayerMark(username, game);
        gameLogicService.makeMove(game, move, playerMark);
        repository.save(game);
        messagingService.sendGameUpdateToOpponent(game);
    }

    @Override
    public List<GameDTO> getActiveGames() {
        List<TicTacToe> games = repository.findAllByStatusNot(GameStatus.FINISHED);
        return games.stream()
                .map(TicTacToe::toDTO)
                .toList();
    }

}
