package com.kkowbel.oneVone.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConnectedUserController {

    private final ConnectedUserService connectedUserService;

    @MessageMapping("/user.connectUser")
    @SendTo("/topic/public")
    public ConnectedUser connectUser(
            @Payload String username,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        return connectedUserService.registerUser(username, headerAccessor);
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/topic/public")
    public ConnectedUser disconnectUser(
            @Payload String username
    ) {
        return connectedUserService.disconnect(username);
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> isUsernameAvailable(
            @PathVariable String username
            ) {
            boolean available = connectedUserService.isUsernameAvailable(username);
            return ResponseEntity.ok(available);
    }

}