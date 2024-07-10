package com.tutor.auth0r.web.websocket;

import com.tutor.auth0r.service.ChatService;
import com.tutor.auth0r.web.websocket.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO message) {
        ChatMessageDTO processedMessage = chatService.processMessage(message);
        messagingTemplate.convertAndSend("/topic/rooms/" + processedMessage.getRoomId(), processedMessage);
    }

    @MessageMapping("/chat.newUser")
    public void newUser(ChatMessageDTO message) {
        chatService.createRoom(message.getSender(), message.getReceiver());
    }
}
