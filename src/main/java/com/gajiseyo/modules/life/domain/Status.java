package com.gajiseyo.modules.life.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    LOOK_FOR("찾아요"),
    I_FOUND("찾았어요");

    private final String displayValue;

}
