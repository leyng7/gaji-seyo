package com.gajiseyo.modules.member.repository.custom;

import com.gajiseyo.infra.querydsl.Querydsl4RepositorySupport;
import com.gajiseyo.modules.member.domain.Member;
import com.gajiseyo.modules.member.dto.MemberDto;
import com.gajiseyo.modules.member.dto.MemberSearch;
import com.gajiseyo.modules.member.dto.QMemberDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static com.gajiseyo.modules.member.domain.QMember.member;


@Transactional(readOnly = true)
public class MemberRepositoryImpl extends Querydsl4RepositorySupport implements MemberRepositoryCustom {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Page<MemberDto> searchAll(Pageable pageable, MemberSearch search) {
        return applyPagination(
            pageable,
            contentQuery -> contentQuery
                .select(new QMemberDto(member)).distinct()
                .from(member)
                .where(
                    ordererNameEq(search.getKeyword())
                )
        );
    }

    private BooleanExpression ordererNameEq(String keyWord) {
        if (keyWord == null || keyWord.isBlank()) {
            return null;
        }

        return null;
    }

}
