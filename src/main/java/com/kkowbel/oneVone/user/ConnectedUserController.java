package com.kkowbel.oneVone.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConnectedUserController {

    private final ConnectedUserService connectedUserService;

    @PostMapping("/connect-user/{username}")
    public ResponseEntity<?> connectUser(
            @PathVariable String username,
            HttpSession session
    ) {
        connectedUserService.connectUser(username, session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> isUsernameAvailable(
            @PathVariable String username
            ) {
            boolean available = connectedUserService.isUsernameAvailable(username);
            return ResponseEntity.ok(available);
    }

    @GetMapping("/connected-users")
    public ResponseEntity<List<ConnectedUser>> getConnectedUsers() {
        return ResponseEntity.ok(connectedUserService.getAllConnectedUsers());
    }


}