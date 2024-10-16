package com.kkowbel.oneVone.user;

import com.kkowbel.oneVone.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
            @Payload String username
    ) {
        ConnectedUser connectedUser = connectedUserService.registerUser(username);
        return connectedUser;
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> isUsernameAvailable(
            @PathVariable String username
            ) {
            boolean available = connectedUserService.isUsernameAvailable(username);
            return ResponseEntity.ok(available);
    }

}
