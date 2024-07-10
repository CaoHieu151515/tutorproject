package com.tutor.auth0r.web.websocket.dto;

public class ChatMessageDTO {

    private String sender;
    private String receiver;
    private String content;
    private String roomId;

    public ChatMessageDTO() {}

    public ChatMessageDTO(String sender, String receiver, String content, String roomId) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.roomId = roomId;
    }

    // Getters and setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
