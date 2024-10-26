package com.kkowbel.oneVone.game;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/active")
    public ResponseEntity<List<GameDTO>> getActiveGames() {
        List<GameDTO> games = gameService.getActiveGames();
        return ResponseEntity.ok(games);
    }
}
