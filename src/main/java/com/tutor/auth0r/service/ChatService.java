package com.tutor.auth0r.service;

import com.tutor.auth0r.web.websocket.dto.ChatMessageDTO;
import com.tutor.auth0r.web.websocket.dto.RoomDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final Map<String, RoomDTO> rooms = new HashMap<>();

    public RoomDTO createRoom(String user1, String user2) {
        String roomId = user1 + "_" + user2;
        if (!rooms.containsKey(roomId)) {
            roomId = user2 + "_" + user1;
        }
        RoomDTO room = new RoomDTO(roomId, user1, user2);
        rooms.put(roomId, room);
        return room;
    }

    public ChatMessageDTO processMessage(ChatMessageDTO message) {
        String roomId = getRoomId(message.getSender(), message.getReceiver());
        return new ChatMessageDTO(message.getSender(), message.getReceiver(), message.getContent(), roomId);
    }

    private String getRoomId(String user1, String user2) {
        String roomId = user1 + "_" + user2;
        if (!rooms.containsKey(roomId)) {
            roomId = user2 + "_" + user1;
        }
        return roomId;
    }
}
