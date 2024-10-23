package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.tictactoe.dto.TicTacToeDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    @GetMapping("/tictactoe/create")
    public ResponseEntity<String> createGame(
            HttpSession session
    ) {
        String newGameId = ticTacToeService.createGame((String)session.getAttribute("username"));
        return ResponseEntity.ok(newGameId);
    }

    @GetMapping("/tictactoe/active-games")
    public List<TicTacToeDTO> getActiveGames() {
        return ticTacToeService.getActiveGames();
    }

    @GetMapping("tictactoe/active-game/{gameId}")
    public ResponseEntity<TicTacToe> getActiveGame(
            @PathVariable String gameId
    ) {
        TicTacToe game = ticTacToeService.getActiveGameById(gameId);
        return ResponseEntity.ok(game);
    }


}
