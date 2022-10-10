package com.gajiseyo.modules.chat.controller;

import com.gajiseyo.modules.chat.dto.Greeting;
import com.gajiseyo.modules.chat.dto.HelloMessage;
import com.gajiseyo.modules.chat.repository.ChatRoomRepository;
import com.gajiseyo.modules.chat.service.ChatService;
import com.gajiseyo.modules.member.auth.CurrentUser;
import com.gajiseyo.modules.member.auth.MemberOauthAdapter;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    @MessageMapping("/hello/{id}")
    @SendTo("/topic/greetings/{id}")
    public Greeting greeting(@DestinationVariable Long id,
                             Principal principal,
                             HelloMessage message) throws Exception {

        OAuth2AuthenticationToken memberOauthAdapter = (OAuth2AuthenticationToken) principal;
        Member member = ((MemberOauthAdapter) memberOauthAdapter.getPrincipal()).getMember();

        chatService.saveChatMessage(id, member, message.getMessage());

        return new Greeting(member.getNickname(), message.getMessage());
    }

    @GetMapping("/chat")
    public String chat(@CurrentUser Member member,
                       Model model) {

        model.addAttribute("chatRoomList", chatRoomRepository.findAll());

        return "user/chat/list";
    }

    @GetMapping("/chat/{id}")
    public String view(@PathVariable Long id,
                       @CurrentUser Member member,
                       Model model) {


        model.addAttribute("chatMessages", chatService.getChatMessages(id, member));
        model.addAttribute("currentUser", member);

        return "user/chat/view";
    }


}
