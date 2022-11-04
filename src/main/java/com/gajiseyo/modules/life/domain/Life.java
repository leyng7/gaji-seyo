package com.gajiseyo.modules.life.domain;

import com.gajiseyo.modules.common.BaseTimeEntity;
import com.gajiseyo.modules.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "TB_LIFE")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Life extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "life_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String contents;

    private int views;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean removed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id")
    private Member createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_up_id")
    private Member lastUpdateBy;

    private Life(Category category, String contents, Member createdBy) {
        this.category = category;
        this.contents = contents;
        this.views = 0;
        this.status = Status.LOOK_FOR;
        this.removed= false;
        this.createdBy = createdBy;
        this.lastUpdateBy = createdBy;
    }

    public static Life create(Category category, String contents, Member createdBy) {
        return new Life(category, contents, createdBy);
    }

    public void update(Category category, String contents) {
        this.category = category;
        this.contents = contents;
    }

}
