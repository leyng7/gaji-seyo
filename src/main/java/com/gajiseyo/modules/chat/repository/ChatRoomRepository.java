package com.gajiseyo.modules.chat.repository;

import com.gajiseyo.modules.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
