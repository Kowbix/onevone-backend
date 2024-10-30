package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.application.SessionService;
import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import com.kkowbel.oneVone.exception.UsernameDontExistsException;
import com.kkowbel.oneVone.websocket.WebSocketMessaging;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WebSocketMessaging webSocketMessaging;
    private final SessionService sessionService;

    @Transactional
    public void connectUser(String username, HttpSession session) {
        if (!isUsernameAvailable(username))
            throw new UsernameAlreadyExistsException("Username '" + username + "' already exists");

        User user = saveConnectedUser(username);
        sessionService.addUsernameToHttpSession(username, user.getUserId(), session);
        notifyUsersAboutChange(user);
    }

    @Transactional
    public User disconnect(String username) {
        User user = userRepository.findConnectedUserByUsername(username)
                .orElseThrow(() -> new UsernameDontExistsException("Username '" + username + "' does not exist"));

        userRepository.delete(user);
        user.setStatus(UserStatus.OFFLINE);
        return user;
    }

    List<User> getAllConnectedUsers() {
        return userRepository.findAll();
    }

    boolean isUsernameAvailable(String username) {
        return !userRepository.findConnectedUserByUsername(username).isPresent();
    }

    public void updateUserStatus(String username, UserStatus newStatus) {
        User user = getConnectedUser(username);
        user.setStatus(newStatus);
        userRepository.save(user);
        notifyUsersAboutChange(user);
    }

    private User saveConnectedUser(String username) {
        User user = new User(username);
        user.setStatus(UserStatus.ONLINE);
        userRepository.save(user);
        return user;
    }

    private void notifyUsersAboutChange(User user) {
        webSocketMessaging.sendNotification(user, "/users");
    }

    private User getConnectedUser(String username) {
        return userRepository.findConnectedUserByUsername(username)
                .orElseThrow(() -> new UsernameDontExistsException("Username '" + username + "' does not exist"));
    }


}
