package com.kkowbel.oneVone.game.tictactoe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString(of = "mark")
@RequiredArgsConstructor
enum PlayerMark {
    X ("X"),
    O ("O");
    private final String mark;
}
