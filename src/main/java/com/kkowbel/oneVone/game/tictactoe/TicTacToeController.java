package com.kkowbel.oneVone.game.tictactoe;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tictactoe")
@RequiredArgsConstructor
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    @PostMapping("/create")
    public ResponseEntity<String> createGame(
            HttpSession session
    ) {
        String newGameId = ticTacToeService.createGame((String)session.getAttribute("username"));
        return ResponseEntity.ok(newGameId);
    }

    @GetMapping("/active-game/{gameId}")
    public ResponseEntity<TicTacToe> getActiveGame(
            @PathVariable String gameId
    ) {
        TicTacToe game = ticTacToeService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/join-to-game/{gameId}")
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

    @PostMapping("/disconnect/{gameId}")
    public ResponseEntity<Void> disconnect(
            @PathVariable String gameId,
            HttpSession session
    ) {
        System.out.println("GameId: " + gameId);
        System.out.println("Username: " + session.getAttribute("username"));
        ticTacToeService.disconnectFromTheGame(gameId, (String)session.getAttribute("username"));
        return ResponseEntity.ok().build();
    }

}
