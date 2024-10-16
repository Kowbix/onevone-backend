package com.kkowbel.oneVone.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectedUserRepository extends JpaRepository<ConnectedUser, String> {

    Optional<ConnectedUser> findConnectedUserByUsername(String username);
    List<ConnectedUser> findConnectedUsersByStatus(ConnectedUserStatus status);
}
