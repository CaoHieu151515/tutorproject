package com.tutor.auth0r.web.websocket;

import com.tutor.auth0r.service.ChatService;
import com.tutor.auth0r.service.impl.AppUserServiceImpl;
import com.tutor.auth0r.web.websocket.dto.ChatMessageDTO;
import com.tutor.auth0r.web.websocket.dto.RoomDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

    private Map<String, List<ChatMessageDTO>> roomMessages = new HashMap<>();
    private Map<String, RoomDTO> userRooms = new HashMap<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    private static final Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO message) {
        // Tạo phòng nếu chưa tồn tại
        log.debug("asdasdasdasdasdsadsadsadsadsa", message);

        String roomId = chatService.getRoomId(message.getSender(), message.getReceiver());
        if (!chatService.roomExists(roomId)) {
            chatService.createRoom(message.getSender(), message.getReceiver());
        }

        // Xử lý và gửi tin nhắn
        ChatMessageDTO processedMessage = chatService.processMessage(message);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, processedMessage);
    }
    // @MessageMapping("/chat.createRoom")
    // public void createRoom(RoomDTO room) {
    //     chatService.createRoom(room.getUser1(), room.getUser2());
    // }

    // @MessageMapping("/chat.userOffline")
    // public void userOffline(ChatUserStatus userStatus) {
    //     String username = userStatus.getUsername();
    //     chatService.userOffline(username);
    //     List<RoomDTO> rooms = chatService.getRoomsByUser(username);
    //     for (RoomDTO room : rooms) {
    //         messagingTemplate.convertAndSend("/topic/rooms/" + room.getRoomId(), new ChatMessageDTO("System", username, username + " is offline.", room.getRoomId()));
    //     }
    // }
}
