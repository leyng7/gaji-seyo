package com.gajiseyo.modules.chat.controller;

import com.gajiseyo.modules.chat.dto.Greeting;
import com.gajiseyo.modules.chat.dto.HelloMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @MessageMapping("/hello/{id}")
    @SendTo("/topic/greetings/{id}")
    public Greeting greeting(@DestinationVariable Long id,
                             HelloMessage message) throws Exception {

        return new Greeting("Hello", message.getMessage());
    }

    @GetMapping("/chat")
    public String chat() {
        return "user/chat/list";
    }


}
