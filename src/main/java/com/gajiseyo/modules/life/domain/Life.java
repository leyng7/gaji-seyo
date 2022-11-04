package com.gajiseyo.modules.life.domain;

import com.gajiseyo.modules.member.domain.Member;

public class Life {

    private Long id;
    private Category category;
    private String contents;
    private int views;
    private Status status;
    private boolean removed;
    private Member createdBy;
    private Member lastUpdateBy;

}
