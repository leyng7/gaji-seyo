package com.gajiseyo.modules.chat.repository;

import com.gajiseyo.modules.chat.domain.ChatMessage;
import com.gajiseyo.modules.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
