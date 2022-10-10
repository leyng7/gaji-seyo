package com.gajiseyo.modules.chat.domain;

import com.gajiseyo.modules.common.BaseTimeEntity;
import com.gajiseyo.modules.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;

@Table(name = "TB_CHAT_MESSAGE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @CreatedBy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", updatable = false)
    private Member from;

    private String message;

    public ChatMessage(ChatRoom chatRoom, Member from, String message) {
        this.chatRoom = chatRoom;
        this.from = from;
        this.message = message;
    }

    public static ChatMessage create(ChatRoom chatRoom, Member from, String message) {
        return new ChatMessage(chatRoom, from, message);
    }

}
