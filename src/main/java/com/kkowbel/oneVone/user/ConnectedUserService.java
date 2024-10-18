package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.websocket.WebSocketManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConnectedUserService {

    private final ConnectedUserRepository connectedUserRepository;
    private final WebSocketManager webSocketManager;
    private final Map<String, ConnectedUser> connectedUsers = new HashMap<>();

    ConnectedUser registerUser(String username, SimpMessageHeaderAccessor headerAccessor) {
        if (!isUsernameAvailable(username)) {
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");
        }
        webSocketManager.addUsernameToWebSocket(headerAccessor, username);
        connectedUserRepository.save(new ConnectedUser(username));
        ConnectedUser connectedUser = new ConnectedUser(username);
        connectedUsers.put(username, connectedUser);
        return connectedUser;
    }

    public ConnectedUser disconnect(String username) {
        ConnectedUser user = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameDontExistsException("Username '" + username + "' does not exist");
        }
        connectedUserRepository.delete(user);
        connectedUsers.remove(username);
        user.setStatus(ConnectedUserStatus.OFFLINE);
        return user;

    }

    List<ConnectedUser> getAllConnectedUsers() {
        return new ArrayList<>(connectedUsers.values());
    }

    public boolean isUsernameAvailable(String username) {
        ConnectedUser connectedUser = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        return connectedUser == null;
    }

}
