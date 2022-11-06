package com.gajiseyo.modules.chat.domain;

import com.gajiseyo.modules.common.BaseTimeEntity;
import com.gajiseyo.modules.item.domain.Item;
import com.gajiseyo.modules.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "TB_CHAT_ROOM")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", updatable = false)
    private Member seller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id", updatable = false)
    private Member buyer;

    public ChatRoom(Item item, Member seller, Member buyer) {
        this.item = item;
        this.seller = seller;
        this.buyer = buyer;
    }

    public static ChatRoom create(Item item, Member seller, Member buyer) {
        return new ChatRoom(item, seller, buyer);
    }

    public boolean isSeller(Member member) {
        return this.seller.equals(member);
    }

}
