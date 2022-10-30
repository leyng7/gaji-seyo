package com.gajiseyo.modules.chat.repository;

import com.gajiseyo.modules.chat.domain.ChatRoom;
import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByItemAndBuyer(Item item, Member Buyer);

}
