package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.application.SessionService;
import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.websocket.WebSocketManager;
import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConnectedUserService {

    private final ConnectedUserRepository connectedUserRepository;
    private final WebSocketMessaging webSocketMessaging;
    private final SessionService sessionService;
    private final Map<String, ConnectedUser> connectedUsers = new HashMap<>();
    

    public void connectUser(String username, HttpSession session) {
        if (!isUsernameAvailable(username)) {
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");
        }
        connectedUserRepository.save(new ConnectedUser(username));
        ConnectedUser connectedUser = new ConnectedUser(username);
        connectedUsers.put(username, connectedUser);
        sessionService.addUsernameToHttpSession(username,connectedUser.getUserId(), session);
        sendUserToUsers(connectedUser);
    }

    public ConnectedUser disconnect(String username) {
        ConnectedUser user = connectedUserRepository.findConnectedUserByUsername(username)
                .orElseThrow(() -> new UsernameDontExistsException("Username '" + username + "' does not exist"));

        connectedUserRepository.delete(user);
        connectedUsers.remove(username);
        user.setStatus(ConnectedUserStatus.OFFLINE);
        return user;
    }

    List<ConnectedUser> getAllConnectedUsers() {
        return connectedUserRepository.findAll();
    }


    public boolean isUsernameAvailable(String username) {
        ConnectedUser connectedUser = connectedUserRepository.findConnectedUserByUsername(username).orElse(null);
        return connectedUser == null;
    }

    private void sendUserToUsers(ConnectedUser user) {
        webSocketMessaging.sendMessageToActiveUsers(user, "/users");
    }


}
