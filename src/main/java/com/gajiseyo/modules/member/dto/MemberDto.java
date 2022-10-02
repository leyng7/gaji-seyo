package com.gajiseyo.modules.member.dto;

import com.gajiseyo.modules.member.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {

    @QueryProjection
    public MemberDto(Member member) {

    }
}
