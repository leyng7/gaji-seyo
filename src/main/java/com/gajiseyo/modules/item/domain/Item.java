package com.gajiseyo.modules.item.domain;

import com.gajiseyo.modules.common.BaseTimeEntity;
import com.gajiseyo.modules.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "TB_ITEM")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String contents;

    private long price;

    private int views;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean removed;

    private boolean suggested;

    private boolean shared;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id")
    private Member createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_up_id")
    private Member lastUpdatedBy;

    private Item(String title, Category category, long price, boolean suggested, boolean shared, String contents, Member createdBy) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.suggested = suggested;
        this.shared = shared;
        this.contents = contents;
        this.views = 0;
        this.status = Status.SALE;
        this.removed = false;
        this.createdBy = createdBy;
        this.lastUpdatedBy = createdBy;
    }

    public static Item create(String title, Category category, long price, boolean suggested, boolean shared, String contents, Member createdBy) {
        return new Item(title, category, price, suggested, shared, contents, createdBy);
    }

    public void update(String title, Category category, long price, boolean suggested, boolean shared, String contents) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.suggested = suggested;
        this.shared = shared;
        this.contents = contents;
    }

}
