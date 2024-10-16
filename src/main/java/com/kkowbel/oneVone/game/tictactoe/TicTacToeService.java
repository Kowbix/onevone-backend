package com.kkowbel.oneVone.game.tictactoe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicTacToeService {

    private final TicTacToeRepository repository;

    private final Map<String, TicTacToe> activeGames = new HashMap<>();




}
