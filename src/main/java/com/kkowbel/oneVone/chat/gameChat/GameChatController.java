package com.kkowbel.oneVone.chat.gameChat;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-chat")
@RequiredArgsConstructor
public class GameChatController {

    private final GameChatService gameChatService;

    @PostMapping("/send")
    public void sendMessageToGameChat(
            @RequestBody GameChatMessageRequestDTO messageDTO,
            HttpSession session
    ) {
        gameChatService.sendMessage(messageDTO, (String) session.getAttribute("username"));
    }
}
