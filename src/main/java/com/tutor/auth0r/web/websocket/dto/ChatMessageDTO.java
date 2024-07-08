package com.tutor.auth0r.web.websocket.dto;

import java.time.Instant;

/**
 * DTO for storing a user's activity.
 */
public class ChatMessageDTO {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
