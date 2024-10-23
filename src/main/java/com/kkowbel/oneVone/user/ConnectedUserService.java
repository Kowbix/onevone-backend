package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.application.SessionService;
import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConnectedUserService {

    private final ConnectedUserRepository connectedUserRepository;
    private final WebSocketMessaging webSocketMessaging;
    private final SessionService sessionService;

    public void connectUser(String username, HttpSession session) {
        if (!isUsernameAvailable(username))
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");

        connectedUserRepository.save(new ConnectedUser(username));
        ConnectedUser connectedUser = new ConnectedUser(username);
        sessionService.addUsernameToHttpSession(username,connectedUser.getUserId(), session);
        sendUserToUsers(connectedUser);
    }

    public ConnectedUser disconnect(String username) {
        ConnectedUser user = connectedUserRepository.findConnectedUserByUsername(username)
                .orElseThrow(() -> new UsernameDontExistsException("Username '" + username + "' does not exist"));

        connectedUserRepository.delete(user);
        user.setStatus(ConnectedUserStatus.OFFLINE);
        return user;
    }

    public void changeUserStatus(String username, ConnectedUserStatus newStatus) {
        ConnectedUser user = connectedUserRepository.findConnectedUserByUsername(username)
                .orElseThrow(() -> new UsernameDontExistsException("Username '" + username + "' does not exist"));
        user.setStatus(newStatus);
        connectedUserRepository.save(user);
        sendUserToUsers(user);
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
