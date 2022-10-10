package com.gajiseyo.modules.chat.service;

import com.gajiseyo.modules.chat.domain.ChatMessage;
import com.gajiseyo.modules.chat.domain.ChatRoom;
import com.gajiseyo.modules.chat.repository.ChatMessageRepository;
import com.gajiseyo.modules.chat.repository.ChatRoomRepository;
import com.gajiseyo.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessages(Long chatRoomId, Member member) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        if (!member.equals(chatRoom.getSeller()) && !member.equals(chatRoom.getBuyer())) {
            throw new AccessDeniedException("권한 없음");
        }

        return chatMessageRepository.findByChatRoom(chatRoom);
    }

    @Transactional
    public void saveChatMessage(Long chatRoomId, Member member, String message) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        ChatMessage chatMessage = ChatMessage.create(
                chatRoom,
                member,
                message
        );

        chatMessageRepository.save(chatMessage);
    }
}
