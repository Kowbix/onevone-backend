package com.kkowbel.oneVone.game.tictactoe;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    @GetMapping("/tictactoe/create")
    public ResponseEntity<TicTacToe> createGame(
            HttpSession session
    ) {
        TicTacToe newGame = ticTacToeService.createGame((String)session.getAttribute("username"));
        return ResponseEntity.ok(newGame);
    }

    @GetMapping("/tictactoe/active-games")
    public List<TicTacToe> getGames() {
        return ticTacToeService.getActiveGames();
    }


}
