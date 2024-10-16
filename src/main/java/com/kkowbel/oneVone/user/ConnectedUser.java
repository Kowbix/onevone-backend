package com.kkowbel.oneVone.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConnectedUser {

    @Id
    @GeneratedValue
    private String userId;
    private String username;

    private LocalDateTime connectedTime;

    private ConnectedUserStatus status;

    public ConnectedUser(String username) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.connectedTime = LocalDateTime.now();
        this.status = ConnectedUserStatus.ONLINE;
    }
}
