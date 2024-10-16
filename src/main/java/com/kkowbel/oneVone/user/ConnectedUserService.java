package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectedUserService {

    private final ConnectedUserRepository connectedUserRepository;

    public ConnectedUser registerUser(String username) {
        if (!isUsernameAvailable(username)) {
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");
        }
        return connectedUserRepository.save(new ConnectedUser(username));
    }

    public synchronized boolean isUsernameAvailable(String username) {
        ConnectedUser connectedUser = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        return connectedUser != null;
    }

}
