package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.GameStatus;
import org.springframework.stereotype.Service;

@Service
class TicTacToeLogic {


    void makeMove(TicTacToe game, TicTacToeMoveDTO move, String playerMark) {
        validateMove(game, move);
        updateBoard(game, move, playerMark);
        switchTurn(game, playerMark);
        checkAndHandleGameOver(game, playerMark);
    }

    private void validateMove(TicTacToe game, TicTacToeMoveDTO move) {
        int row = move.getRow();
        int col = move.getCol();

        if (row < 0 || row >= game.getBoard().length || col < 0 || col >= game.getBoard().length) {
            throw new IllegalArgumentException("Invalid row or column: " + row + ", " + col);
        }

        if (game.getBoard()[row][col] != null && !game.getBoard()[row][col].equals(" ")) {
            throw new IllegalArgumentException("Field [" + row + "][" + col + "] is occupied");
        }
    }

    private void updateBoard(TicTacToe game, TicTacToeMoveDTO move, String playerMark) {
        String[][] board = game.getBoard();
        board[move.getRow()][move.getCol()] = playerMark;
        game.setBoard(board);
    }

    private void switchTurn(TicTacToe game, String playerMark) {
        game.setTurn(playerMark.equals("X") ? game.getPlayer2() : game.getPlayer1());
    }

    private void checkAndHandleGameOver(TicTacToe game, String playerMark) {
        checkAndHandleWin(game, playerMark);
        if(game.getStatus() != GameStatus.WON) checkAndHandleDraw(game);
    }

    private void checkAndHandleWin(TicTacToe game, String playerMark) {
        if (isWinningMove(game.getBoard(), playerMark)) {
            game.setWinner(game.getTurn());
            game.setTurn("");
            game.setStatus(GameStatus.WON);
        }
    }

    private void checkAndHandleDraw(TicTacToe game) {
        if (isDrawMove(game.getBoard())) {
            game.setTurn("");
            game.setStatus(GameStatus.DRAW);
        }
    }

    private boolean isWinningMove(String[][] board, String playerMark) {
        return checkRows(board, playerMark) || checkColumns(board, playerMark) || checkDiagonals(board, playerMark);
    }

    private boolean isDrawMove(String[][] board) {
        for (String[] row : board) {
            for (String col : row) {
                if (col.equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRows(String[][] board, String playerMark) {
        for (String[] row : board) {
            if (row[0].equals(playerMark) &&
                    row[1].equals(playerMark) &&
                    row[2].equals(playerMark)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(String[][] board, String playerMark) {
        for (int col = 0; col < board.length; col++) {
            if(board[0][col].equals(playerMark) &&
                board[1][col].equals(playerMark) &&
                board[2][col].equals(playerMark)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(String[][] board, String playerMark) {
        if (board[0][0].equals(playerMark) &&
                board[1][1].equals(playerMark) &&
                board[2][2].equals(playerMark)) {
            return true;
        }

        if (board[0][2].equals(playerMark) &&
                board[1][1].equals(playerMark) &&
                board[2][0].equals(playerMark)) {
            return true;
        }

        return false;
    }
}
