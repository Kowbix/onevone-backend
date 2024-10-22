package com.kkowbel.oneVone.game.tictactoe;

import com.kkowbel.oneVone.game.Game;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicTacToe extends Game {

    private final String gameName = "tictactoe";
    private String turn;
    private String[][] board;

    public TicTacToe(String player1) {
        super(player1);
        this.turn = player1;
        this.board = initNewBoard();
    }

//    protected JSONObject toJson(JSONObject object) {
//        object.put("gameName", getGameName());
//        object.put("gameId", getGameId());
//        object.put("player1", getPlayer1());
//        object.put("player2", getPlayer2());
//        object.put("winner", getWinner());
//        object.put("gameDate", getGameDate());
//        object.put("status", getStatus());
//        object.put("turn", getTurn());
//        object.put("board", getBoard());
//        return object;
//    }

    private String[][] initNewBoard() {
        String[][] newBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard[i][j] = " ";
            }
        }
        return newBoard;
    }


}
