package com.kkowbel.oneVone.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private String userId;
    private String username;

    private LocalDateTime connectedTime;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(String username) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.connectedTime = LocalDateTime.now();
    }
}
