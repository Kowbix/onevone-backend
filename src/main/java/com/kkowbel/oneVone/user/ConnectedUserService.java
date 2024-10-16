package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectedUserService {

    private final ConnectedUserRepository connectedUserRepository;

    @Transactional
    public ConnectedUser registerUser(String username) {
        if (!isUsernameAvailable(username)) {
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");
        }
        return connectedUserRepository.save(new ConnectedUser(username));
    }

    public boolean isUsernameAvailable(String username) {
        ConnectedUser connectedUser = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        return connectedUser == null;
    }

}
