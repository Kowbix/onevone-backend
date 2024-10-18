package com.kkowbel.oneVone.game.tictactoe;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicTacToeController {

    private final TicTacToeService ticTacToeService;

    @GetMapping("tictactoe/create")
    public TicTacToe createGame(
    ) {

        return ticTacToeService.createGame(username);
    }


}
