package com.tutor.auth0r.web.websocket;

import static com.tutor.auth0r.config.WebsocketConfiguration.IP_ADDRESS;

import com.tutor.auth0r.web.websocket.dto.ChatMessageDTO;
import java.security.Principal;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.util.HtmlUtils;

@Controller
public class TutorService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public TutorService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // public static class MessageRequest {

    //     private String message;

    //     public String getMessage() {
    //         return message;
    //     }

    //     public void setMessage(String message) {
    //         this.message = message;
    //     }
    // }
    class ChatMessage {

        private String content;
        private String sender;

        public ChatMessage() {}

        public ChatMessage(String content, String sender) {
            this.content = content;
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }

    // @MessageMapping("/tutor")
    // @SendTo("/topic/messages")
    // public ChatMessage sendMessage(ChatMessage message) {
    //     // Xử lý hoặc lưu tin nhắn nếu cần
    //     return new ChatMessage(HtmlUtils.htmlEscape(message.getContent()), HtmlUtils.htmlEscape(message.getSender()));
    // }
    @MessageMapping("/topic/tutor")
    @SendTo("/topic/messages")
    public String handleMessage(String message) {
        log.info("Received message: {}", message);
        return message;
    }

    // @MessageMapping("/topic/chatroom/{chatroomId}/send-message")
    // @SendTo("/topic/chatroom/{chatroomId}/notification")
    // public ChatMessageDTO sendActivity(@Payload ChatMessageDTO chatMessageDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
    //     //activityDTO.setUserLogin(principal.getName());
    //     //activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
    //     //activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
    //     //activityDTO.setTime(Instant.now());

    //     log.debug("Sending user chat message data {}", chatMessageDTO);
    //     return chatMessageDTO;
    // }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        //messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
    }
}
