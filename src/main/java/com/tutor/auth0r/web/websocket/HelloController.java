package com.tutor.auth0r.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greet() {
        return "i";
    }
}
