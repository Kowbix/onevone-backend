package com.kkowbel.oneVone.game.tictactoe;

import org.springframework.stereotype.Service;

@Service
public class TicTacToeLogic {


    void makeMove(TicTacToe game, TicTacToeMoveDTO move, String playerMark) {
        updateBoard(game, move, playerMark);
        if (isWinningMove(game)) {
            game.setWinner(game.getTurn());
            return;
        }
        switchTurn(game, playerMark);
    }

    private boolean isWinningMove(TicTacToe game) {
        return false;
    }

    private void updateBoard(TicTacToe game, TicTacToeMoveDTO move, String playerMark) {
        String[][] board = game.getBoard();
        int row = move.getRow();
        int col = move.getCol();

        if(row < 0 || row >= game.getBoard().length || col < 0 || col >= game.getBoard().length)
            throw new IllegalArgumentException("Invalid row or column: " + row + ", " + col);

        if (board[row][col] != null && !board[row][col].equals(" "))
            throw new IllegalArgumentException("Field [" + row + "][" + col + "] is occupied");

        board[row][col] = playerMark;
        game.setBoard(board);
    }

    private void switchTurn(TicTacToe game, String playerMark) {
        if (playerMark.equals("X")) game.setTurn(game.getPlayer2());
        else game.setTurn(game.getPlayer1());
    }
}
