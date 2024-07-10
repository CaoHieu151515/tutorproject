// package com.tutor.auth0r.web.websocket;

// import static com.tutor.auth0r.config.WebsocketConfiguration.IP_ADDRESS;

// import com.tutor.auth0r.web.websocket.dto.ChatMessageDTO;
// import java.security.Principal;
// import java.time.Instant;
// import java.util.HashMap;
// import java.util.Map;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.ApplicationListener;
// import org.springframework.messaging.handler.annotation.*;
// import org.springframework.messaging.simp.SimpMessageSendingOperations;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.socket.messaging.SessionDisconnectEvent;
// import org.springframework.web.util.HtmlUtils;

// @Controller
// public class TutorService implements ApplicationListener<SessionDisconnectEvent> {

//     private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

//     private final SimpMessageSendingOperations messagingTemplate;

//     public TutorService(SimpMessageSendingOperations messagingTemplate) {
//         this.messagingTemplate = messagingTemplate;
//     }

//     private Map<String, String> userRooms = new HashMap<>();

//     @MessageMapping("/chat.sendMessage")
//     @SendTo("/topic/messages")
//     public ChatMessageDTO sendMessage(ChatMessageDTO message) {
//         String roomId = getRoomId(message.getSender(), message.getReceiver());
//         return new ChatMessageDTO(message.getSender(), message.getReceiver(),
//                 HtmlUtils.htmlEscape(message.getContent()), roomId);
//     }

//     @MessageMapping("/chat.newUser")
//     public void newUser(ChatMessageDTO message) {
//         String roomId = getRoomId(message.getSender(), message.getReceiver());
//         userRooms.put(message.getSender(), roomId);
//         userRooms.put(message.getReceiver(), roomId);
//     }
//     private String getRoomId(String sender, String receiver) {
//         String roomId = sender + "_" + receiver;
//         if (!userRooms.containsKey(roomId)) {
//             roomId = receiver + "_" + sender;
//         }
//         return roomId;
//     }

//     // public static class MessageRequest {

//     // private String message;

//     // public String getMessage() {
//     // return message;
//     // }

//     // public void setMessage(String message) {
//     // this.message = message;
//     // }
//     // }

//     // @MessageMapping("/tutor")
//     // @SendTo("/topic/messages")
//     // public ChatMessage sendMessage(ChatMessage message) {
//     // // Xử lý hoặc lưu tin nhắn nếu cần
//     // return new ChatMessage(HtmlUtils.htmlEscape(message.getContent()),
//     // HtmlUtils.htmlEscape(message.getSender()));
//     // }
//     // @MessageMapping("/topic/tutor")
//     // @SendTo("/topic/messages")
//     // public String handleMessage(String message) {
//     // log.info("Received message: {}", message);
//     // return message;
//     // }

//     // @MessageMapping("/topic/chatroom/{chatroomId}/send-message")
//     // @SendTo("/topic/chatroom/{chatroomId}/notification")
//     // public ChatMessageDTO sendActivity(@Payload ChatMessageDTO chatMessageDTO,
//     // StompHeaderAccessor stompHeaderAccessor, Principal principal) {
//     // //activityDTO.setUserLogin(principal.getName());
//     // //activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
//     // //activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
//     // //activityDTO.setTime(Instant.now());

//     // log.debug("Sending user chat message data {}", chatMessageDTO);
//     // return chatMessageDTO;
//     // }

//     @Override
//     public void onApplicationEvent(SessionDisconnectEvent event) {
//         // messagingTemplate.convertAndSend("/topic/tracker", activityDTO);
//     }
// }
