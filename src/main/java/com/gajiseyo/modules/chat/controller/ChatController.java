package com.gajiseyo.modules.chat.controller;

import com.gajiseyo.modules.chat.domain.ChatMessage;
import com.gajiseyo.modules.chat.domain.ChatRoom;
import com.gajiseyo.modules.chat.dto.Greeting;
import com.gajiseyo.modules.chat.dto.HelloMessage;
import com.gajiseyo.modules.chat.repository.ChatRoomRepository;
import com.gajiseyo.modules.chat.service.ChatService;
import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.item.repository.ItemRepository;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final ItemRepository itemRepository;

    @MessageMapping("/hello/{id}")
    @SendTo("/topic/greetings/{id}")
    public Greeting greeting(@DestinationVariable Long id,
                             Principal principal,
                             HelloMessage message) throws Exception {

        OAuth2AuthenticationToken memberOauthAdapter = (OAuth2AuthenticationToken) principal;
        Member member = ((MemberOauthAdapter) memberOauthAdapter.getPrincipal()).getMember();

        ChatMessage chatMessage = chatService.saveChatMessage(id, member, message.getMessage());

        return new Greeting(
                member.getId(),
                member.getNickname(),
                message.getMessage(),
                chatMessage.getCreatedDate()
        );
    }

    @GetMapping("/chat")
    public String chat(@CurrentUser Member member,
                       Model model) {

        model.addAttribute("chatRoomList", chatRoomRepository.findAll());

        return "user/chat/list";
    }

    @GetMapping("/chat/{id}")
    public String view(@PathVariable Long id,
                       @CurrentUser Member currentUser,
                       Model model) {

        model.addAttribute("chatMessages", chatService.getChatMessages(id, currentUser));
        model.addAttribute("currentUser", currentUser);

        return "user/chat/view";
    }

    @GetMapping("/items/{id}/chat")
    public String list(@PathVariable Long id,
                       @CurrentUser Member currentUser,
                       Model model) {

        Item item = itemRepository.findById(id).orElseThrow();

        Optional<ChatRoom> findChatRoom = chatRoomRepository.findByItemAndBuyer(item, currentUser);

        ChatRoom chatRoom = findChatRoom.orElseGet(() -> {
            ChatRoom newChartRoom = ChatRoom.create(
                    item,
                    item.getCreatedBy(),
                    currentUser
            );

            chatRoomRepository.save(newChartRoom);

            return newChartRoom;
        });

        model.addAttribute("chatMessages", chatService.getChatMessages(chatRoom.getId(), currentUser));
        model.addAttribute("currentUser", currentUser);

        return "user/chat/view";
    }


}
