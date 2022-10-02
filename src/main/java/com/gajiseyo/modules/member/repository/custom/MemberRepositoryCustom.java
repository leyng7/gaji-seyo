package com.gajiseyo.modules.member.repository.custom;

import com.gajiseyo.modules.member.dto.MemberDto;
import com.gajiseyo.modules.member.dto.MemberSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<MemberDto> searchAll(Pageable pageable, MemberSearch search);

}
