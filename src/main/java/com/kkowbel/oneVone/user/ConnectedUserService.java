package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectedUserService {

    private final ConnectedUserRepository connectedUserRepository;

    @Transactional
    public ConnectedUser registerUser(String username, SimpMessageHeaderAccessor headerAccessor) {
        if (!isUsernameAvailable(username)) {
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");
        }
        headerAccessor.getSessionAttributes().put("username", username);
        return connectedUserRepository.save(new ConnectedUser(username));
    }

    @Transactional
    public ConnectedUser disconnect(String username) {
        ConnectedUser user = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameDontExistsException("Username '" + username + "' does not exist");
        }
        connectedUserRepository.delete(user);
        user.setStatus(ConnectedUserStatus.OFFLINE);
        return user;
    }

    public boolean isUsernameAvailable(String username) {
        ConnectedUser connectedUser = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        return connectedUser == null;
    }

}
