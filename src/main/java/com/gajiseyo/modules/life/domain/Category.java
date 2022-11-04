package com.gajiseyo.modules.life.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    NEWS("동네소식"),
    RESTAURANT("동네맛집"),
    DAILY_LIFE("일상"),
    LOST_MISSING("분실/실종센터");

    private final String displayValue;

}
