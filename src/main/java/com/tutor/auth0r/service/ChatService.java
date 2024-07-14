package com.tutor.auth0r.service;

import com.tutor.auth0r.web.websocket.dto.ChatMessageDTO;
import com.tutor.auth0r.web.websocket.dto.RoomDTO;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final Map<String, RoomDTO> rooms = new HashMap<>();
    private final Map<String, List<ChatMessageDTO>> messageHistory = new HashMap<>();

    public RoomDTO createRoom(String user1, String user2) {
        String roomId = getRoomId(user1, user2);
        RoomDTO room = new RoomDTO(roomId, user1, user2);
        rooms.put(roomId, room);
        return room;
    }

    public ChatMessageDTO processMessage(ChatMessageDTO message) {
        String roomId = getRoomId(message.getSender(), message.getReceiver());
        // Lưu tin nhắn vào lịch sử
        messageHistory.computeIfAbsent(roomId, k -> new ArrayList<>()).add(message);

        return message;
    }

    public List<ChatMessageDTO> getMessageHistory(String roomId) {
        return messageHistory.getOrDefault(roomId, new ArrayList<>());
    }

    public boolean roomExists(String roomId) {
        return rooms.containsKey(roomId);
    }

    public String getRoomId(String user1, String user2) {
        String roomId = user1 + "_" + user2;
        if (!rooms.containsKey(roomId)) {
            roomId = user2 + "_" + user1;
        }
        return roomId;
    }
}
