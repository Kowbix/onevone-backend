package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.GameDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tictactoe")
@RequiredArgsConstructor
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    @GetMapping("/create")
    public ResponseEntity<String> createGame(
            HttpSession session
    ) {
        String newGameId = ticTacToeService.createGame((String)session.getAttribute("username"));
        return ResponseEntity.ok(newGameId);
    }

    @GetMapping("/active-games")
    public List<GameDTO> getActiveGames() {
        return ticTacToeService.getActiveGames();
    }

    @GetMapping("/active-game/{gameId}")
    public ResponseEntity<TicTacToe> getActiveGame(
            @PathVariable String gameId
    ) {
        TicTacToe game = ticTacToeService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/join-to-game/{gameId}")
    public ResponseEntity<Void> joinToGame(
            @PathVariable String gameId,
            HttpSession session
    ) {
        ticTacToeService.joinToGame(gameId, (String)session.getAttribute("username"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/make-move")
    public ResponseEntity<Void> makeMove(
            @RequestBody TicTacToeMoveDTO move,
            HttpSession session
    ) {
        ticTacToeService.playTurn(move, (String)session.getAttribute("username"));
        return ResponseEntity.ok().build();
    }


}
