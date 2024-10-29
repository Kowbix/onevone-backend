package com.kkowbel.oneVone.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public abstract class Message {

    private String message;
    private String sender;
    private String time;

    public Message(String message, String sender) {
        this.message = message;
        this.sender = sender;
        this.time = getMessageTime();
    }

    public abstract String getMessagePath();

    private String getMessageTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return currentTime.format(formatter);
    }
}
