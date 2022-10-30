package com.gajiseyo.modules.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Greeting {

    private Long memberId;

    private String nickName;

    private String message;

    @JsonFormat(pattern = "a hh:mm")
    private LocalDateTime createdDate;


}
