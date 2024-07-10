package com.tutor.auth0r.web.websocket.dto;

public class RoomDTO {

    private String roomId;
    private String user1;
    private String user2;

    public RoomDTO() {}

    public RoomDTO(String roomId, String user1, String user2) {
        this.roomId = roomId;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
