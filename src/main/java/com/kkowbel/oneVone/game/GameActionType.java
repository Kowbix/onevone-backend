package com.kkowbel.oneVone.game;

public enum GameActionType {

    JOIN("joined the game"),
    LEAVE("left the game");

    private final String actionMessage;

    GameActionType(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getActionMessage() {
        return actionMessage;
    }
}
